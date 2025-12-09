package com.rockstreamer.iscreensdk.listeners

import android.content.Context

interface  oniScreenPremiumCallBack {
    fun onPremiumContentClick(context: Context, slug: String, type:String)
    fun onTokenInvalid(tokenValid: Boolean)
}