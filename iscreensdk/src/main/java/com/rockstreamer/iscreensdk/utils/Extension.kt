package com.rockstreamer.iscreensdk.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.format.DateUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.palette.graphics.Palette
import com.google.gson.Gson
import com.rockstreamer.iscreensdk.IScreenActivity
import com.rockstreamer.iscreensdk.R
import com.rockstreamer.iscreensdk.activity.SeeMoreActivity
import com.rockstreamer.iscreensdk.activity.SeriesDetailsActivity
import com.rockstreamer.iscreensdk.activity.VideoDetailsActivity
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import com.rockstreamer.iscreensdk.pojo.ProfileData
import com.rockstreamer.iscreensdk.pojo.others.Cast
import com.rockstreamer.iscreensdk.pojo.others.Genres
import com.rockstreamer.iscreensdk.pojo.series.Directors
import es.dmoral.toasty.Toasty
import kotlin.math.log


fun getHeaders():Map<String, String>{
    val settings: MutableMap<String, String> = HashMap()
    settings["Referer"] = "https://iscreen.com.bd/"
    settings["Origin"] = "https://iscreen.com.bd"
    return settings
}


const val CC_GROUP_INDEX_MOD = 1000
const val CC_DISABLED = -1001
const val CC_DEFAULT = -1000


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


fun Context.showSuccessToast(message:String){
    Toasty.success(applicationContext , "$message" , Toast.LENGTH_SHORT , false).show()
}

//fun Context.showErrorToast(message: String){
//    Toasty.error(applicationContext , "$message" , Toast.LENGTH_SHORT , false).show()
//}

fun Context.openSeriesDetailsActivity(id:String){
    var intent = Intent(this, SeriesDetailsActivity::class.java)
    intent.putExtra(SERIES_ID_PASS, "" + id)
    startActivity(intent)
}

fun Context.openSeeMoreDetails(id:String, title:String , imageType: String, context: Context){
    SeeMoreActivity.setInterfaceInstance(context = context)
    var intent = Intent(this, SeeMoreActivity::class.java)
    intent.putExtra(EXTRA_SEE_MORE_ID, "" + id)
    intent.putExtra(EXTRA_SEEMORE_TITLE, "" + title)
    intent.putExtra(EXTRA_SEEMORE_ORIENTATION, "" + imageType)
    startActivity(intent)
}

fun Context.getSubscriptionInformation(): ProfileData {
    return Gson().fromJson(JWTUtils.decoded(loginState.getString(API_TOKEN , "")), ProfileData::class.java)
}
fun Context.openDetailsScreen(id:String , type:String, callback: oniScreenPremiumCallBack){
    when(type){
        "video" ->{
            val intent = Intent(this, VideoDetailsActivity::class.java)
            intent.putExtra(VIDEO_ID_PASS, "" + id)
            VideoDetailsActivity.setInterfaceInstance(callback)
            startActivity(intent)
        }

        "series" ->{
            val intent = Intent(this, SeriesDetailsActivity::class.java)
            intent.putExtra(SERIES_ID_PASS, "" + id)
            SeriesDetailsActivity.setInterfaceInstance(callback)
            startActivity(intent)
        }
    }
}


fun Context.openiScreenContentFromBl(id:String , type:String, callback: oniScreenPremiumCallBack){

    when(type){
        "video" ->{
            var intent = Intent(this, VideoDetailsActivity::class.java)
            intent.putExtra(VIDEO_ID_PASS, "" + id)
            VideoDetailsActivity.setInterfaceInstance(callback)
            startActivity(intent)
        }

        "series" ->{
            var intent = Intent(this, SeriesDetailsActivity::class.java)
            intent.putExtra(SERIES_ID_PASS, "" + id)
            SeriesDetailsActivity.setInterfaceInstance(callback)
            startActivity(intent)
        }
    }
}


fun Context.openVideoDetailsActivityWithoutAds(id:String){
    var intent = Intent(this, VideoDetailsActivity::class.java)

    intent.putExtra(VIDEO_ID_PASS, "" + id)
    startActivity(intent)
}



fun processDirector(item: List<Directors>):String{
    var audioCast = item as java.util.ArrayList<Directors>
    val jokeStringBuilder = StringBuilder()

    for ((i , audio) in audioCast.withIndex()){
        if (i == audioCast.size-1){
            jokeStringBuilder.append(audio.name + "   ")
        }
        else{
            jokeStringBuilder.append(audio.name + "  ,  ")
        }
    }

    var castName = jokeStringBuilder.toString()
    return castName
}



fun processCast(item: List<Cast>):String{
    var audioCast = item as java.util.ArrayList<Cast>
    val jokeStringBuilder = StringBuilder()
    for ((i , audio) in audioCast.withIndex()){
        if (i == audioCast.size-1){
            jokeStringBuilder.append(audio.name + "   ")
        }
        else{
            jokeStringBuilder.append(audio.name + "  ,  ")
        }
    }

    var castName = jokeStringBuilder.toString()
    return castName
}




fun Long.millisToFormattedDuration(): String {
    if (this < 0) {
        return "--:--"
    }
    val seconds = this % DateUtils.MINUTE_IN_MILLIS / DateUtils.SECOND_IN_MILLIS
    val minutes = this % DateUtils.HOUR_IN_MILLIS / DateUtils.MINUTE_IN_MILLIS
    val hours = this % DateUtils.DAY_IN_MILLIS / DateUtils.HOUR_IN_MILLIS

    return if (hours > 0) {
        "%d:%02d:%02d".format(hours, minutes, seconds)
    } else "%02d:%02d".format(minutes, seconds)
}



fun getMostPopulousSwatch(palette: Palette?): Palette.Swatch? {
    var mostPopulous: Palette.Swatch? = null
    if (palette != null) {
        for (swatch in palette.swatches) {
            if (mostPopulous == null || swatch.population > mostPopulous.population) {
                mostPopulous = swatch
            }
        }
    }
    return mostPopulous
}



fun showGenre(generList: java.util.ArrayList<Genres>): String {
    val jokeStringBuilder = StringBuilder()
    for ((i, it) in generList.withIndex()) {
        if(i == generList.size - 1){
            jokeStringBuilder.append(it.name + "   ")
        }else{
            jokeStringBuilder.append(it.name + "  |  ")
        }
    }
    return jokeStringBuilder.toString()

}



fun bindFeaturebutton( type:String, button: AppCompatButton){
    when(type){
        "video" -> button.text = button.context.resources.getString(R.string.watch_now)
        "series" -> button.text = button.context.resources.getString(R.string.watch_now)
    }
}


fun getContentType(type:String): Int{
    return when(type){
        "video" ->{
            VIDEO_CONTENT
        }
        "series" ->{
            SERIES_CONTENT
        }
        else -> {
            VIDEO_CONTENT
        }
    }
}

fun Context.stopIScreen(){
    IScreenActivity.stopiScreen()
    SeeMoreActivity.stopiScreen()
    VideoDetailsActivity.stopiScreen()
    SeriesDetailsActivity.stopiScreen()
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
