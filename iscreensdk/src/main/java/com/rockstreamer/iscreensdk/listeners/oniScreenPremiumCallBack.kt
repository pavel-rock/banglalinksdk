package com.rockstreamer.iscreensdk.listeners

import android.content.Context

interface  oniScreenPremiumCallBack {
    fun onPremiumContentClick(context: Context, contentId: String , type:Int)
    fun onTokenInvalid()
}