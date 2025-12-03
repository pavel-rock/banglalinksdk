package com.rockstreamer.iscreensdk.utils

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast


class WebViewJsInterface(private val context: Context) {

    @JavascriptInterface
    fun tokenStatus(value: String, message: String) {
        Log.d("APP_STATUS", "comes into token status")
        Toast.makeText(context, "" + value, Toast.LENGTH_LONG).show()
        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show()
    }

    @JavascriptInterface
    fun contentChange(path: String, isPremium: String) {
        Log.d("APP_STATUS", "comes into content change")
        Log.d("APP_STATUS", "is premium: ${isPremium}")
        Toast.makeText(context, "is premium" + isPremium, Toast.LENGTH_LONG).show()
        Toast.makeText(context, "" + path, Toast.LENGTH_LONG).show()
    }

    @JavascriptInterface
    fun eventExecute(params: String, value:String) {
        Log.d("APP_STATUS", "event execute")
        Toast.makeText(context, "event $params", Toast.LENGTH_LONG).show()
        Toast.makeText(context, "value $value", Toast.LENGTH_LONG).show()
    }


}
