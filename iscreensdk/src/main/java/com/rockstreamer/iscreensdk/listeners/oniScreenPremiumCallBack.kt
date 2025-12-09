package com.rockstreamer.iscreensdk.listeners

interface  oniScreenPremiumCallBack {
    fun onPremiumContentClick(isPremium : Boolean)
    fun onTokenInvalid(tokenValid: Boolean)
}