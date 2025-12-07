package com.rockstreamer.iscreensdk.utils



import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rockstreamer.iscreensdk.IScreenActivity

class CustomWebViewClient(
    private val context: Context,
    private val loadingCallback: (Boolean) -> Unit
) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        loadingCallback(true)
        Log.d("CustomWebViewClient", "Start: $url")
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        loadingCallback(false)
        Log.d("CustomWebViewClient", "Finished: $url")

        if (context is IScreenActivity && url != null) {
            context.setCurrentUrl(url)
        }
    }
}