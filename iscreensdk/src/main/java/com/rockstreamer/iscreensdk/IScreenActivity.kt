package com.rockstreamer.iscreensdk

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import com.rockstreamer.iscreensdk.utils.*

class IScreenActivity :
    AppCompatActivity(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        private const val TAG = "IScreenActivity"

        var callback: oniScreenPremiumCallBack? = null
        var context: Context? = null

        fun setInterfaceInstance(call: oniScreenPremiumCallBack) {
            callback = call
        }

        fun stopiScreen() {
            (context as? Activity)?.finish()
        }
    }

    private lateinit var webView: WebView

    private var currentUrl: String = ""
    private val HOME_URL = "https://iscreen.com.bd/"

    fun setCurrentUrl(url: String) {
        currentUrl = url
    }

    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        context = this
        registerOnSharedPreferenceChangedListener(this)

        webView = findViewById(R.id.webview)
        progressBar = findViewById(R.id.progressbar)

        val url = intent.getStringExtra(CONTENT_URL) ?: BASE_URL
        Log.d("APP_STATUS", "Initial URL: $url")

        initWebView(url)
        initBackPressHandler()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        findViewById<ImageView>(R.id.toolbar_back).setOnClickListener {
            goBack()
        }

        findViewById<ImageView>(R.id.toolbar_close).setOnClickListener {
            finish()
        }
    }

    private fun initWebView(url: String) {

        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false

        // Attach WebViewClient with progress handling
        webView.webViewClient = CustomWebViewClient(this) { isLoading ->
            showLoading(isLoading)
        }

        // Attach JS interface
        callback?.let { cb ->
            webView.addJavascriptInterface(
                WebViewJsInterface(this, callback = cb),
                "iscreen"
            )
        }

        webView.clearHistory()
        webView.clearCache(true)

        // ChromeClient for console logs
        webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                Log.d(
                    TAG,
                    "${consoleMessage.message()} -- line ${consoleMessage.lineNumber()} of ${consoleMessage.sourceId()}"
                )
                return true
            }
        }

        val token = loginState.getString(API_TOKEN, "")
        val fullUrl = "$url?token=$token"
        Log.d("APP_STATUS", "WebView loading: $fullUrl")

        webView.loadUrl(fullUrl)
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun initBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                Log.d("APP_STATUS", "Current URL = $currentUrl")

                // If on homepage → close immediately
                if (currentUrl.startsWith(HOME_URL)) {
                    finish()
                } else {
                    goBack()
                }
            }
        })
    }

    private fun goBack() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finish()
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        initWebView(BASE_URL)
    }

    override fun onDestroy() {
        unregisterOnSharedPreferenceChangedListener(this)
        webView.destroy()
        super.onDestroy()
    }
}