package com.rockstreamer.iscreensdk.utils

import android.content.Context
import android.os.Build
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack


class WebViewJsInterface(private val context: Context, val callback: oniScreenPremiumCallBack) {

    @JavascriptInterface
    fun tokenStatus(value: Boolean, message: String) {
        Log.d("APP_STATUS", "token valid $value")
        callback.onTokenInvalid(value)
    }

    private var lastTime = 0L
    private val THROTTLE_MS = 1000 // allow once per second

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @JavascriptInterface
    fun contentChange(path: String, isPremium: Boolean) {
        Log.d("APP_STATUS", "eventExecute $path")

        val now = System.currentTimeMillis()
        if (now - lastTime < THROTTLE_MS) return
        lastTime = now
        var playParts : PlayParts? = parseAfterPlay(path)
        playParts?.slug?.let { callback.onPremiumContentClick(context = context , slug = it, type = playParts.type) }
    }

    @JavascriptInterface
    fun eventExecute(params: String, value:String) {
        Log.d("APP_STATUS", "eventExecute $params $value")
    }

}
