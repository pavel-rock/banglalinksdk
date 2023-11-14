package com.rockstreamer.iscreensdk.listeners

import com.rockstreamer.iscreensdk.pojo.recommand.RecommendedResponse


interface OnRecommandCallback {
    fun onRecommandClick(response: RecommendedResponse)
}