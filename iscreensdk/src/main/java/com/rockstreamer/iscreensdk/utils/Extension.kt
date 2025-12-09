package com.rockstreamer.iscreensdk.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.rockstreamer.iscreensdk.IScreenActivity
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import java.net.URI
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


const val BASE_URL = "https://iscreen.com.bd/"

fun SharedPreferences.putAny(tag: String, any: Any) {
    when (any) {
        is Boolean -> edit().putBoolean(tag, any).apply()
        is String -> edit().putString(tag, any).apply()
        is Int -> edit().putInt(tag, any).apply()
    }
}

fun SharedPreferences.clean(){
    edit().clear()
}


data class PlayParts(val type: String, val slug: String)

/** Returns the segments after `/play/` -> (type, slug), or null if not matched. */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun parseAfterPlay(url: String): PlayParts? = runCatching {
    // Clean weird line-separator chars that sometimes sneak in from copy/paste
    val cleaned = url.trim()
        .replace("\u2028", "") // LS
        .replace("\u2029", "") // PS

    val segments = URI(cleaned).path
        .split('/')
        .filter { it.isNotEmpty() }

    val playIdx = segments.indexOf("play")
    if (playIdx == -1) return null

    val type = segments.getOrNull(playIdx + 1)?.lowercase() ?: return null
    val rawSlug = segments.getOrNull(playIdx + 2) ?: return null
    val slug = URLDecoder.decode(rawSlug, StandardCharsets.UTF_8)

    PlayParts(type, slug)
}.getOrNull()


fun Context.openiScreenContentFromBl(id:String , type:String, callback: oniScreenPremiumCallBack){
    IScreenActivity.setInterfaceInstance(callback)
    var url = "${BASE_URL}content/details/${type}/${id}"
    var intent = Intent(this , IScreenActivity::class.java)
    intent.putExtra(CONTENT_URL , url)
    startActivity(intent)

}

fun Context.stopIScreen(){
    IScreenActivity.stopiScreen()
}

fun Context.openiScreenSDK(callback: oniScreenPremiumCallBack){
    IScreenActivity.setInterfaceInstance(callback)
    startActivity(Intent(this, IScreenActivity::class.java))
}


fun Context.registerOnSharedPreferenceChangedListener(
    listener: SharedPreferences.OnSharedPreferenceChangeListener,
) = loginState.registerOnSharedPreferenceChangeListener(listener)


fun Context.unregisterOnSharedPreferenceChangedListener(
    changeListener: SharedPreferences.OnSharedPreferenceChangeListener,
) = loginState.unregisterOnSharedPreferenceChangeListener(changeListener)


fun iScreenSDKInit(apiToken: String){
    loginState.putAny(API_TOKEN, apiToken)
}

fun cleanIScreenSDK(){
    loginState.clean()
}

//fun oniScreenTokenStatus():Boolean{
//
//}
//
//
//fun openContentFromMyBlApp(){
//
//}

fun View.gone() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}
