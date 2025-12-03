package com.rockstreamer.iscreensdk.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import com.rockstreamer.iscreensdk.IScreenActivity
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack


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



fun Context.openiScreenContentFromBl(id:String , type:String, callback: oniScreenPremiumCallBack){
    IScreenActivity.setInterfaceInstance(callback)
    var url = "${BASE_URL}/content/details/${type}/${id}"
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
