package com.rockstreamer.videoplayer.builder

import android.content.Context
import com.google.android.exoplayer2.LoadControl
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.upstream.BandwidthMeter

data class PlayerConfig(
    val context: Context,
    val bandwidthMeter: BandwidthMeter,
    val trackManager: TrackManager,
    val loadControl: LoadControl,
    val rendererFactory: RenderersFactory,
    val mediaSourceFactory: MediaSourceFactory,
    var isLive: Boolean
)