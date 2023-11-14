package com.rockstreamer.videoplayer.listeners

import java.time.Duration

interface ExoPlayerEventListeners {

    fun onBuffering(value: Boolean)
    fun onVideoEnd()
    fun onIdle()
    fun onReady()

    fun onVideoProgressUpdate(duration: Long)

    fun onImaAdsShow(value: Boolean)
}