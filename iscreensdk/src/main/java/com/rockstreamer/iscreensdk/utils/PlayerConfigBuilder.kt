package com.rockstreamer.iscreensdk.utils

import android.content.Context
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.rockstreamer.videoplayer.renderer.PlayerRendererFactory

import com.rockstreamer.videoplayer.builder.PlayerConfig
import com.rockstreamer.videoplayer.builder.TrackManager

class PlayerConfigBuilder(private val context: Context) {
    private var bandwidthMeter: BandwidthMeter? = null
    private var trackManager: TrackManager? = null
    private var rendererFactory: RenderersFactory? = null

    fun build(): PlayerConfig {
        val rendererFactory = rendererFactory ?: PlayerRendererFactory(context)
        return PlayerConfig(
            context = context,
            bandwidthMeter = bandwidthMeter ?: DefaultBandwidthMeter.Builder(context).build(),
            trackManager = trackManager ?: TrackManager(context),
            loadControl = DefaultLoadControl(),
            mediaSourceFactory = getDataSourceFactory(),
            isLive = false,
            rendererFactory = rendererFactory
        )
    }

    private fun getDataSourceFactory(): DefaultMediaSourceFactory {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory().apply {
            setDefaultRequestProperties(getHeaders())
        }
        return DefaultMediaSourceFactory(dataSourceFactory)
    }
}