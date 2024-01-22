package com.rockstreamer.iscreensdk.player.player

import android.net.Uri
import com.google.ads.interactivemedia.v3.api.player.AdMediaInfo
import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.analytics.AnalyticsCollector
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Clock
import com.rockstreamer.iscreensdk.utils.Repeater
import com.rockstreamer.iscreensdk.utils.getHeaders
import com.rockstreamer.videoplayer.listeners.ExoPlayerEventListeners
import com.rockstreamer.videoplayer.builder.PlayerConfig
import com.rockstreamer.videoplayer.builder.TrackManager
import com.rockstreamer.videoplayer.listeners.ExoMediaPlayer
import com.rockstreamer.videoplayer.renderer.RendererType
class ExoMediaPlayerImpl(private val config: PlayerConfig, private val playerView: PlayerView): Player.Listener,ExoMediaPlayer {

    private var adsLoader: ImaAdsLoader? = null
    private var mediaItem: MediaItem?=null
    private var mediaSourceFactory: MediaSourceFactory?=null
    private var exoPlayerEventListeners: ExoPlayerEventListeners?=null
    private var progressPollRepeater = Repeater()
    private var mCurrentVideoPosition: Long? =0

    fun setExoplayerEventListeners(listeners: ExoPlayerEventListeners){
        this.exoPlayerEventListeners = listeners
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        when(playbackState){
            Player.STATE_READY -> {
                exoPlayerEventListeners?.onBuffering(value = false)
                exoPlayerEventListeners?.onReady()
                progressPollRepeater.start()
                progressPollRepeater.repeatListener ={
                    exoPlayerEventListeners?.onVideoProgressUpdate(currentPosition)
                }
            }
            Player.STATE_ENDED -> {
                progressPollRepeater.stop()
                progressPollRepeater.repeatListener = null
                exoPlayerEventListeners?.onVideoEnd()
            }
            Player.STATE_BUFFERING -> exoPlayerEventListeners?.onBuffering(value = true)
            Player.STATE_IDLE -> exoPlayerEventListeners?.onIdle()

        }
    }

    private val exoPlayer: ExoPlayer by lazy {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory().apply {
            setDefaultRequestProperties(getHeaders())
        }

            adsLoader = ImaAdsLoader.Builder(config.context).setAdEventListener {}.setVideoAdPlayerCallback(object : VideoAdPlayer.VideoAdPlayerCallback{
                override fun onAdProgress(p0: AdMediaInfo?, p1: VideoProgressUpdate?) {

                }

                override fun onBuffering(p0: AdMediaInfo?) {

                }

                override fun onContentComplete() {
                    exoPlayerEventListeners?.onImaAdsShow(value = false)
                }

                override fun onEnded(p0: AdMediaInfo?) {
                    exoPlayerEventListeners?.onImaAdsShow(value = false)
                }

                override fun onError(p0: AdMediaInfo?) {
                    exoPlayerEventListeners?.onImaAdsShow(value = false)
                }

                override fun onLoaded(p0: AdMediaInfo?) {

                }

                override fun onPause(p0: AdMediaInfo?) {

                }

                override fun onPlay(p0: AdMediaInfo?) {
                    exoPlayerEventListeners?.onImaAdsShow(value = true)
                }

                override fun onResume(p0: AdMediaInfo?) {

                }

                override fun onVolumeChanged(p0: AdMediaInfo?, p1: Int) {

                }

            }).build()
            mediaSourceFactory = DefaultMediaSourceFactory(dataSourceFactory).setAdsLoaderProvider(
                DefaultMediaSourceFactory.AdsLoaderProvider { adsLoader }).setAdViewProvider(playerView)

        config.trackManager.getSelector()
        ExoPlayer.Builder(
            config.context,
            config.rendererFactory,
            mediaSourceFactory as DefaultMediaSourceFactory,
            config.trackManager.selector,
            config.loadControl,
            config.bandwidthMeter,
            AnalyticsCollector(Clock.DEFAULT)
        ).build().also {
            it.addListener(this)
            playerView.player = it

            adsLoader!!.setPlayer(it)
        }
    }


//        .setSeekBackIncrementMs(10000)
//        .setSeekForwardIncrementMs(10000)
    override val playing: Boolean get() = exoPlayer.isPlaying
    override val duration: Long get() = exoPlayer.duration
    override val currentPosition: Long get() = exoPlayer.currentPosition

    override var getExoPlayer: ExoPlayer = exoPlayer


    override fun setMediaUri(uri: Uri?, isAdsShow: Boolean, vastTag: String) {
        mediaItem = if (isAdsShow){
            MediaItem.Builder().setUri(uri).setAdTagUri(vastTag).build()
        }else {
            MediaItem.Builder().setUri(uri).build()
        }
        mediaItem?.let { exoPlayer.setMediaItem(it) }
        prepare()
    }


    override fun setMediaUri(uri: Uri?, duration: Long, isAdsShow: Boolean, vastTag: String) {
        mediaItem = if (isAdsShow){
            MediaItem.Builder().setUri(uri).setAdTagUri(vastTag).setClippingConfiguration(MediaItem.ClippingConfiguration.Builder().setEndPositionMs(duration).build()).build()
        }else {
           MediaItem.Builder().setUri(uri).setClippingConfiguration(MediaItem.ClippingConfiguration.Builder().setEndPositionMs(duration).build()).build()
        }

        mediaItem?.let { exoPlayer.setMediaItem(it) }
        prepare()
    }

    override fun prepare() {
        exoPlayer.stop()
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }


    override fun forcePrepare() {
        exoPlayer.playWhenReady = true
    }

    override fun release() {
        progressPollRepeater.stop()
        progressPollRepeater.repeatListener = null
        exoPlayer.playWhenReady = false
        exoPlayer.release()
    }

    override fun start() {
        exoPlayer.playWhenReady = true
    }

    override fun onPause() {
        mCurrentVideoPosition = currentPosition
        exoPlayer.playWhenReady = false
    }

    override fun onResume() {
        exoPlayer.playWhenReady = true
        mCurrentVideoPosition?.let { seekTo(it) }
    }

    override fun seekTo(duration: Long) {
        exoPlayer.seekTo(duration)
    }

    override fun getTrackManager(): TrackManager = currentTrackSelector


    override val currentTrackSelector: TrackManager
        get() = config.trackManager


    override val availableTracks: Map<RendererType, TrackGroupArray>?
        get() = config.trackManager.getAvailableTracks()

    override fun trackSelectionAvailable(): Boolean {
        return true
    }
    override fun setSelectedTrack(type: RendererType, groupIndex: Int, trackIndex: Int) {
        config.trackManager.setSelectedTrack(type, groupIndex, trackIndex)
    }

    override fun getSelectedTrackIndex(type: RendererType, groupIndex: Int): Int {
        return config.trackManager.getSelectedTrackIndex(type, groupIndex)
    }

    override fun isRendererEnabled(type: RendererType): Boolean {
        return config.trackManager.isRendererEnabled(type)
    }
}