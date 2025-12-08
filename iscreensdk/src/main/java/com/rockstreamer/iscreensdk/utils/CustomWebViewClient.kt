package com.rockstreamer.iscreensdk.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.RenderProcessGoneDetail
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.rockstreamer.iscreensdk.IScreenActivity
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLDecoder
import java.util.concurrent.ConcurrentHashMap

class CustomWebViewClient(
    private val context: Context,
    private val loadingCallback: (Boolean) -> Unit
) : WebViewClient() {

    private val TAG = "WV-DIAG"

    /** Track start time of each requested URL (best-effort). */
    private val startTimes = ConcurrentHashMap<String, Long>()

    /** Poll Resource Timing a few times after start/finish. */
    private val handler = Handler(Looper.getMainLooper())
    private val pendingPolls = mutableListOf<Runnable>()

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        loadingCallback(true)
        startTimes.clear()
        cancelResourceTimingPolls()
        Log.d(TAG, "▶ onPageStarted: $url")

        // Kick off early polls so we catch slow/late resources
        view?.let { scheduleResourceTimingPolls(it, phase = "started") }
    }

    @RequiresApi(23)
    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)
        Log.d(TAG, "🖼 first content visible: $url")
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        loadingCallback(false)
        Log.d(TAG, "⏹ onPageFinished: $url")

        // Final timing dump (navigation + slowest resources)
        view?.let {
            dumpNavigationTiming(it)
            scheduleResourceTimingPolls(it, phase = "finished")
        }

        if (context is IScreenActivity && url != null) {
            context.setCurrentUrl(url)
        }
    }

    // ---- Resource start/end visibility -------------------------------------

    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
        if (url == null) return
        // onLoadResource is called when a resource is loaded (may be called multiple times),
        // we use it to estimate duration since shouldInterceptRequest().
        val now = SystemClock.elapsedRealtime()
        val start = startTimes.remove(url)
        if (start != null) {
            Log.d(TAG, "✅ loaded: ${short(url)}  ~${now - start}ms")
        } else {
            Log.d(TAG, "✅ loaded (no start seen): ${short(url)}")
        }
    }

    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest): WebResourceResponse? {
        val url = request.url.toString()
        val now = SystemClock.elapsedRealtime()
        startTimes.putIfAbsent(url, now)

        val main = if (request.isForMainFrame) "MAIN" else "SUB"
        val method = request.method
        val ref = request.requestHeaders["Referer"] ?: "-"
        Log.d(TAG, "➡ $main $method ${short(url)}  referer=${short(ref)}")
        return super.shouldInterceptRequest(view, request)
    }

    // For older API (<21): keep a lightweight log
    @Deprecated("API <21 path")
    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        if (url != null) {
            startTimes.putIfAbsent(url, SystemClock.elapsedRealtime())
            Log.d(TAG, "➡ SUB GET ${short(url)}")
        }
        return super.shouldInterceptRequest(view, url)
    }

    // ---- Error visibility ---------------------------------------------------

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        Log.w(
            TAG,
            "💥 onReceivedError: url=${request?.url} code=${error?.errorCode} desc=${error?.description}"
        )
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        response: WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, response)
        Log.w(
            TAG,
            "🚫 HTTP ${response?.statusCode} for ${request?.url} reason=${response?.reasonPhrase}"
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
        Log.e(TAG, "🔥 Render process gone. Did the page use too much memory? crashed=${detail?.didCrash()}")
        return super.onRenderProcessGone(view, detail)
    }

    // ---- JS: Navigation / Resource Timing ----------------------------------

    private fun dumpNavigationTiming(webView: WebView) {
        val js = """
            (function() {
              try {
                const nav = performance.getEntriesByType('navigation')[0] || performance.timing;
                function val(n){return (typeof n==='number')?n:0}
                const base = nav.startTime || (nav.navigationStart||0);
                const summary = {
                  type: nav.type ? (''+nav.type) : 'unknown',
                  ttfb: Math.round((nav.responseStart||0) - base),
                  domContentLoaded: Math.round((nav.domContentLoadedEventEnd||0) - base),
                  loadEvent: Math.round((nav.loadEventEnd||0) - base),
                  transferSize: nav.transferSize||0,
                  encodedBodySize: nav.encodedBodySize||0,
                  decodedBodySize: nav.decodedBodySize||0
                };
                return JSON.stringify(summary);
              } catch(e){ return JSON.stringify({error: ''+e}); }
            })();
        """.trimIndent()

        webView.evaluateJavascript(js, ValueCallback { json ->
            try {
                val obj = JSONObject(json ?: "{}")
                Log.d(
                    TAG,
                    "⏱ nav: ttfb=${obj.optInt("ttfb")}ms, dcl=${obj.optInt("domContentLoaded")}ms, load=${obj.optInt("loadEvent")}ms, " +
                            "xfer=${obj.optLong("transferSize")}B, enc=${obj.optLong("encodedBodySize")}B, dec=${obj.optLong("decodedBodySize")}B, type=${obj.optString("type")}"
                )
            } catch (t: Throwable) {
                Log.w(TAG, "nav timing parse failed: $json", t)
            }
        })
    }

    private fun scheduleResourceTimingPolls(webView: WebView, phase: String) {
        // Poll a few times (captures late XHRs, fonts, images)
        repeat(5) { i ->
            val delay = (i + 1) * 1000L
            val r = Runnable { dumpResourceTiming(webView, "$phase#$i") }
            pendingPolls += r
            handler.postDelayed(r, delay)
        }
    }

    private fun cancelResourceTimingPolls() {
        pendingPolls.forEach { handler.removeCallbacks(it) }
        pendingPolls.clear()
    }

    private fun dumpResourceTiming(webView: WebView, label: String) {
        val js = """
            (function() {
              try {
                const items = performance.getEntriesByType('resource')
                  .map(r => ({
                    name: r.name,
                    type: r.initiatorType || 'other',
                    dur: Math.round(r.duration||0),
                    end: Math.round(r.responseEnd||0),
                    tsize: r.transferSize||0,
                    enc: r.encodedBodySize||0,
                    dec: r.decodedBodySize||0
                  }));
                // Top 10 by duration (slowest)
                items.sort((a,b)=>b.dur - a.dur);
                const top = items.slice(0, 10);
                return JSON.stringify({count: items.length, top});
              } catch(e) { return JSON.stringify({error: ''+e}); }
            })();
        """.trimIndent()

        webView.evaluateJavascript(js) { json ->
            try {
                val root = JSONObject(json ?: "{}")
                val cnt = root.optInt("count")
                val arr = root.optJSONArray("top") ?: JSONArray()
                Log.d(TAG, "📊 RT($label): $cnt resources, top slow:")
                for (i in 0 until arr.length()) {
                    val o = arr.getJSONObject(i)
                    val nm = short(o.optString("name"))
                    val tp = o.optString("type")
                    val dur = o.optInt("dur")
                    val tsize = o.optLong("tsize")
                    Log.d(TAG, "  • ${i+1}. $tp ${dur}ms ${bytes(tsize)} $nm")
                }
            } catch (t: Throwable) {
                Log.w(TAG, "resource timing parse failed: $json", t)
            }
        }
    }

    // ---- Helpers ------------------------------------------------------------

    private fun short(u: String?): String {
        if (u.isNullOrBlank()) return "-"
        return try {
            val s = URLDecoder.decode(u, "UTF-8")
            when {
                s.length <= 120 -> s
                else -> s.take(100) + "…" + s.takeLast(15)
            }
        } catch (_: Throwable) {
            if (u.length <= 120) u else u.take(100) + "…" + u.takeLast(15)
        }
    }

    private fun bytes(n: Long): String {
        if (n <= 0) return "0B"
        val units = arrayOf("B","KB","MB","GB")
        val idx = (Math.log10(n.toDouble()) / Math.log10(1024.0)).toInt().coerceIn(0, units.lastIndex)
        val v = n / Math.pow(1024.0, idx.toDouble())
        return String.format("%.1f%s", v, units[idx])
    }

    // Optional: call once from your Activity to see page console logs in Logcat.
    companion object {
        fun attachConsoleLogger(webView: WebView) {
            webView.webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(message: ConsoleMessage): Boolean {
                    Log.d(
                        "WV-CONSOLE",
                        "${message.messageLevel()} ${message.sourceId()}:${message.lineNumber()} -> ${message.message()}"
                    )
                    return true
                }
            }
        }
    }
}