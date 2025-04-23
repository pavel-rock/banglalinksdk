package com.rockstreamer.iscreensdk.utils

import android.content.Context
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject


class MixpanelAnalytics(val context: Context) {

    var mixpanel = MixpanelAPI.getInstance(context, "a299ebca8050cbae3c5bfdf8963ec465", false)


    fun trackContent(contentTitle:String){
        val screenObject = JSONObject()
        screenObject.put("my_bl_content", contentTitle)
        mixpanel.track("my_bl_content", screenObject)
    }

    fun trackBlOpen(eventName:String){
        val screenObject = JSONObject()
        screenObject.put("my_bl_sdk_open", eventName)
        mixpanel.track("my_bl_sdk_open", screenObject)
    }

    fun trackPremiumContent(eventName:String){
        val screenObject = JSONObject()
        screenObject.put("my_bl_premium_content", eventName)
        mixpanel.track("my_bl_premium_content", screenObject)
    }

}