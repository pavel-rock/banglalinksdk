package com.rockstreamer.iscreensdk.listeners

import com.rockstreamer.iscreensdk.pojo.slider.SliderResponse


interface onBannerCallback {
    fun onBannerClick(item: SliderResponse)
}