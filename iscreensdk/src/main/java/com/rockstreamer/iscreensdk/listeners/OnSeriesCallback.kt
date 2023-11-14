package com.rockstreamer.iscreensdk.listeners

import com.rockstreamer.iscreensdk.pojo.series.EpisodeItem


interface OnSeriesCallBack {
    fun onSeriesCallback(EpisodeItem: EpisodeItem, position:Int)
}