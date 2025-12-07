package com.rockstreamer.iscreensdk.listeners

interface  oniScreenPremiumCallBack {
    fun onPremiumContentClick(isPremium : Boolean , contentPath: String)
    fun onTokenInvalid()
}