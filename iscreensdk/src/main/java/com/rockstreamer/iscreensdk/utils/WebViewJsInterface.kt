package com.rockstreamer.iscreensdk.utils

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack


class WebViewJsInterface(private val context: Context, val callback: oniScreenPremiumCallBack) {

    @JavascriptInterface
    fun tokenStatus(value: Boolean, message: String) {
        Log.d("APP_STATUS", "token valid $value")
    }

    private var lastTime = 0L
    private val THROTTLE_MS = 1000 // allow once per second

    @JavascriptInterface
    fun contentChange(path: String, isPremium: Boolean) {
        val now = System.currentTimeMillis()

        if (now - lastTime < THROTTLE_MS) return
        lastTime = now
        callback.onPremiumContentClick(isPremium, path)
    }

    @JavascriptInterface
    fun eventExecute(params: String, value:String) {

    }

}
