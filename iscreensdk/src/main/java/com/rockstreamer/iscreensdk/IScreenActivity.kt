package com.rockstreamer.iscreensdk

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.rockstreamer.iscreensdk.utils.CustomWebViewClient
import com.rockstreamer.iscreensdk.utils.WebViewJsInterface
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import com.rockstreamer.iscreensdk.utils.API_TOKEN
import com.rockstreamer.iscreensdk.utils.BASE_URL
import com.rockstreamer.iscreensdk.utils.CONTENT_URL
import com.rockstreamer.iscreensdk.utils.loginState
import com.rockstreamer.iscreensdk.utils.registerOnSharedPreferenceChangedListener
import com.rockstreamer.iscreensdk.utils.unregisterOnSharedPreferenceChangedListener

class IScreenActivity : AppCompatActivity() , SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var webView: WebView
    companion object{
        var callback: oniScreenPremiumCallBack?=null
        private const val TAG = "CustomWebViewClient"
        var context: Context?= null
        fun setInterfaceInstance(callBack: oniScreenPremiumCallBack){
            this.callback = callBack
        }

        fun stopiScreen(){
            if (context!=null){
                (context as Activity).finish()
            }
        }

    }

    private var currentUrl: String = ""
    private val HOME_URL = "https://iscreen.com.bd/"

    fun setCurrentUrl(url: String) {
        currentUrl = url
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        context = this
        webView = findViewById(R.id.webView)

        Log.d("APP_STATUS" , "comes on onCreate")
        registerOnSharedPreferenceChangedListener(this)

        val url = intent.getStringExtra(CONTENT_URL) ?: BASE_URL
        initWebView(url)

        initBackPressHandler()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    private fun initWebView(url : String) {
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false

        webView.webViewClient =
            CustomWebViewClient(this)
        webView.addJavascriptInterface(WebViewJsInterface(this), "iscreen")
        webView.clearHistory()
        webView.clearCache(true)

        webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                Log.d(
                    TAG,
                    "${consoleMessage.message()} -- From line ${consoleMessage.lineNumber()} of ${consoleMessage.sourceId()}"
                )
                return true
            }
        }
        Log.d("APP_STATUS", "Comes into the webview")
        webView.loadUrl("${url}?token=${loginState.getString(API_TOKEN, "")}")
        //webView.loadUrl("https://www.chorki.com/")
    }


    private fun initBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                // If we are on the real home page → exit immediately
                Log.d("APP_STATUS", " $currentUrl")
                if (currentUrl == HOME_URL) {
                    finish()
                    return
                }

                // Otherwise, normal WebView back behavior
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        })
    }


    override fun onDestroy() {
        webView.destroy()
        unregisterOnSharedPreferenceChangedListener(this)
        super.onDestroy()
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        initWebView(BASE_URL)
    }
}