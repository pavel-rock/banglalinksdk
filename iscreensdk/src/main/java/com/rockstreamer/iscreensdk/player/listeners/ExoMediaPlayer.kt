package com.rockstreamer.videoplayer.listeners

import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.TrackGroupArray
import com.rockstreamer.videoplayer.builder.TrackManager
import com.rockstreamer.videoplayer.renderer.RendererType

interface ExoMediaPlayer {

    val playing: Boolean
    val duration: Long
    val currentPosition:Long
    var getExoPlayer: ExoPlayer
    fun setMediaUri(uri: Uri?, isAdsShow: Boolean = false , vastTag: String = "")
    fun prepare()
    fun forcePrepare()
    fun release()
    fun start()
    fun setMediaUri(uri: Uri?, duration: Long, isAdsShow: Boolean = false , vastTag:String = "")

    fun onPause()
    fun onResume()
    fun seekTo(duration: Long)
    fun getTrackManager(): TrackManager

    val availableTracks: Map<RendererType, TrackGroupArray>?

    fun trackSelectionAvailable(): Boolean
    fun setSelectedTrack(type: RendererType, groupIndex: Int, trackIndex: Int)

    fun getSelectedTrackIndex(type: RendererType, groupIndex: Int): Int

    val currentTrackSelector: TrackManager?

    fun isRendererEnabled(type: RendererType): Boolean
}