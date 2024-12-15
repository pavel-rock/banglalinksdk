package com.rockstreamer.iscreensdk.listeners

import com.rockstreamer.iscreensdk.pojo.seemore.SeeMoreItems

interface OnSeeMoreContentListener {
    fun onSeeMoreContentClick(seeMoreItems: SeeMoreItems)
}