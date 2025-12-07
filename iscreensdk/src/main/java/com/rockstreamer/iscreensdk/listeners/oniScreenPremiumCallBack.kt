package com.rockstreamer.iscreensdk.listeners

import android.content.Context

interface  oniScreenPremiumCallBack {
    fun onPremiumContentClick(isPremium : Boolean , contentPath: String)
    fun onTokenInvalid()
}