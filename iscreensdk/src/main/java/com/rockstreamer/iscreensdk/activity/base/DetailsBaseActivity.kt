package com.rockstreamer.iscreensdk.activity.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.rockstreamer.iscreensdk.R
import com.rockstreamer.iscreensdk.api.viewmodel.details.VideoDetailsViewModel
import com.rockstreamer.iscreensdk.api.viewmodel.recommand.RecommandViewModel
import com.rockstreamer.iscreensdk.api.viewmodel.series.SeriesViewModel
import com.rockstreamer.iscreensdk.listeners.OnSeriesDetailsListeners
import com.rockstreamer.iscreensdk.pojo.others.Genres
import com.rockstreamer.iscreensdk.utils.CustomAlertDialog
import com.rockstreamer.iscreensdk.utils.PADDING
import com.rockstreamer.iscreensdk.utils.PlayerConfigBuilder
import com.rockstreamer.iscreensdk.utils.ScreenUtil
import com.rockstreamer.iscreensdk.utils.gone
import com.rockstreamer.iscreensdk.utils.rotation.Orientation
import com.rockstreamer.iscreensdk.utils.show
import com.rockstreamer.videoplayer.listeners.ExoPlayerEventListeners
import com.rockstreamer.iscreensdk.player.player.ExoMediaPlayerImpl
import com.rockstreamer.videoplayer.quality.QualityPopupManager
import org.koin.android.ext.android.inject

interface onDeviceRotation{
    fun onFullScreenButtonClick()
    fun onExitFullScreenButtonClick()
    fun onDeviceLeftSideRotate(): Boolean
    fun onDeviceRightSideRotate() : Boolean
}

abstract class DetailsBaseActivity: AppCompatActivity() , Orientation.Listener, ExoPlayerEventListeners {
    val videoDetailsViewModel : VideoDetailsViewModel by inject()
    val recommandViewModel: RecommandViewModel by inject()
    val seriesViewModel: SeriesViewModel by inject()
    var mOrientation: Orientation? = null
    lateinit var alertDialog: AlertDialog
    private lateinit var progressDialog: ProgressDialog
    private lateinit var exoLoading: ProgressBar
    var isFullScreen = false

    lateinit var onSeriesDetailsListeners : OnSeriesDetailsListeners

    fun setDetailsListeners(listeners: OnSeriesDetailsListeners){
        this.onSeriesDetailsListeners = listeners
    }


    lateinit var enterFullScreenButton: AppCompatImageButton
    lateinit var videoQualityButton: AppCompatImageButton

    lateinit var playerZoomButton: AppCompatImageButton
    var isZoomScan = false

    val qualityPopupManager = QualityPopupManager()

    fun showNextPreviousController(value: Boolean){
        if (value){
            nextButton.show()
            previousButton.show()
        }else{
            nextButton.gone()
            previousButton.gone()
        }
    }

    // Listeners
    lateinit var onDeviceRotation: onDeviceRotation

    abstract fun onCreateDetailsView(savedInstanceState: Bundle?)
    abstract fun onStartDetails()
    abstract fun onStopDetails()

    abstract fun onDestroyDetails()

    abstract fun onDownloadStart()
    abstract fun onDownloadFinished()
    abstract fun onDownloadError()
    abstract fun onAlreadyDownloaded(isDownload: Boolean)

    abstract fun onDownloadStops()


    abstract fun onNextClick()
    abstract fun onPreviousClick()

    abstract fun onDownloadPause()
    lateinit var playerView: PlayerView


    val corePlayer : ExoMediaPlayerImpl by lazy {
        ExoMediaPlayerImpl(PlayerConfigBuilder(context = this).build(), playerView)
    }
    lateinit var nextButton: ImageButton
    lateinit var previousButton: ImageButton

    fun setExoPlayer(playerView: PlayerView, exoLoading: ProgressBar, nextButton: ImageButton, previousButton: ImageButton){
        this.playerView = playerView
        this.exoLoading = exoLoading
        this.nextButton = nextButton
        this.previousButton = previousButton
    }
    override fun onOrientationChanged(pitch: Float, roll: Float) {
        when(roll){
            in 65.0..95.0 -> {
                if (isFullScreen){
                    if (!onDeviceRotation.onDeviceLeftSideRotate()){
                        onDeviceRotation.onDeviceLeftSideRotate()
                    }
                }
            }

            in 150.00.. 190.00 ->{
                if (isFullScreen){
                    if (!onDeviceRotation.onDeviceRightSideRotate()){
                        onDeviceRotation.onDeviceRightSideRotate()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyDetails()
        if (mOrientation!=null){
            mOrientation!!.stopListening();
        }
        corePlayer.release()
    }

    override fun onPause() {
        super.onPause()
        corePlayer.onPause()
    }

    override fun onResume() {
        super.onResume()
        corePlayer.onResume()
    }
    override fun onStop() {
        super.onStop()
        onStopDetails()
        if (mOrientation!=null){
            mOrientation!!.stopListening();
        }
    }

    override fun onStart() {
        super.onStart()
        onStartDetails()
    }
    fun setOnDeviceRotationListener(onDeviceRotation: onDeviceRotation){
        this.onDeviceRotation = onDeviceRotation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.iscreen_toolbar_color)
        alertDialog = CustomAlertDialog.showAlertDialog(this)
        playerZoomButton = AppCompatImageButton(this)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(null)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Preparing Download Options...")


        mOrientation = Orientation(this)
        onCreateDetailsView(savedInstanceState = savedInstanceState)
        corePlayer.setExoplayerEventListeners(this)

    }

    fun screenRotateToLeftSide(videoView: PlayerView, videoFrameLayout: FrameLayout, isOffline: Boolean){
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        videoView.layoutParams = FrameLayout.LayoutParams( // or ViewGroup.LayoutParams.WRAP_CONTENT
            FrameLayout.LayoutParams.MATCH_PARENT,  // or ViewGroup.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        videoFrameLayout.layoutParams = LinearLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        isFullScreen = true
        if (!isOffline){
            enterFullScreenButton.setImageResource(R.drawable.ic_exit_fullscreen)
        }
    }

    fun screenRotateToRightSide(videoView: PlayerView, videoFrameLayout: FrameLayout, isOffline: Boolean){
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        videoView.layoutParams = FrameLayout.LayoutParams( // or ViewGroup.LayoutParams.WRAP_CONTENT
                FrameLayout.LayoutParams.MATCH_PARENT,  // or ViewGroup.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.MATCH_PARENT)

        videoFrameLayout.layoutParams = LinearLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT)
        isFullScreen = true
        if (!isOffline){
            enterFullScreenButton.setImageResource(R.drawable.ic_exit_fullscreen)
        }
    }

    fun fullScreenEnter(videoView: PlayerView, videoFrameLayout: FrameLayout){
        if (!isFullScreen){
            screenRotateToLeftSide(videoView = videoView , videoFrameLayout = videoFrameLayout, isOffline = false)
            mOrientation!!.startListening(this)
        } else {
            val display = (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay
            val orientation = display.orientation
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            videoView.layoutParams =
                FrameLayout.LayoutParams( // or ViewGroup.LayoutParams.WRAP_CONTENT
                    FrameLayout.LayoutParams.WRAP_CONTENT,  // or ViewGroup.LayoutParams.WRAP_CONTENT,
                    ScreenUtil.convertDIPToPixels(
                        this,
                        270
                    )
                )

            videoFrameLayout.layoutParams =
                LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    ScreenUtil.convertDIPToPixels(
                        this,
                        270
                    )
                )

            isFullScreen = false
            enterFullScreenButton.setImageResource(R.drawable.ic_enter_fullscreen)
            if (mOrientation!=null){
                mOrientation!!.stopListening();
            }
            isZoomScan = false
            videoView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            playerZoomButton.setImageResource(R.drawable.ic_zoom_out)
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        adjustFullScreen(newConfig)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            adjustFullScreen(resources.configuration)
        }
    }


    private fun adjustFullScreen(config: Configuration) {
        val insetsController = ViewCompat.getWindowInsetsController(window.decorView)
        insetsController?.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            insetsController?.hide(WindowInsetsCompat.Type.systemBars())
        } else {
            insetsController?.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    fun showGeners(generList: ArrayList<Genres>, genreText: AppCompatTextView) {
        val jokeStringBuilder = StringBuilder()
        for ((i, it) in generList.withIndex()) {
            if(i == generList.size - 1){
                jokeStringBuilder.append(it.name + "   ")
            }else{
                jokeStringBuilder.append(it.name + "  |  ")
            }
        }
        genreText.text = jokeStringBuilder.toString()
    }


    fun setupController(linearLayout: LinearLayout){

        nextButton.setOnClickListener {
            onNextClick()
        }

        previousButton.setOnClickListener {
            onPreviousClick()
        }


        playerZoomButton.apply {
            setBackgroundResource(android.R.color.transparent)
            setImageResource(R.drawable.ic_zoom_out)
            setPadding(PADDING , PADDING , PADDING , PADDING)
            setOnClickListener {
                if (!isZoomScan){
                    isZoomScan = true
                    //playerView.setScaleType(ScaleType.CENTER_CROP)
                    playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    playerZoomButton.setImageResource(R.drawable.ic_zoom_in)
                }else{
                    isZoomScan = false
                    if (isFullScreen){
                        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
                        playerZoomButton.setImageResource(R.drawable.ic_zoom_out)
                    }else{
                        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                        playerZoomButton.setImageResource(R.drawable.ic_zoom_out)
                    }
                }
            }
        }

        videoQualityButton = AppCompatImageButton(this).apply {
            setBackgroundResource(android.R.color.transparent)
            setImageResource(R.drawable.ic_video_quality)
            setPadding(PADDING , PADDING , PADDING , PADDING)
            setOnClickListener {
                qualityPopupManager.showQualityPopup(qualityPopupManager , corePlayer , videoQualityButton)
            }
        }
        enterFullScreenButton = AppCompatImageButton(this).apply {
            setBackgroundResource(android.R.color.transparent)
            setImageResource(R.drawable.ic_enter_fullscreen)
            setPadding(PADDING , 15 , PADDING , PADDING)
            setOnClickListener {
                onDeviceRotation.onFullScreenButtonClick()
            }
        }

        linearLayout.addView(playerZoomButton)
        linearLayout.addView(videoQualityButton)
        linearLayout.addView(enterFullScreenButton)
    }


    override fun onBuffering(value: Boolean) {
        if (value){
            exoLoading.show()
        }else{
            exoLoading.gone()
        }
    }

    fun playContent(url:String){
        corePlayer.setMediaUri(uri = Uri.parse(url), vastTag = " " , isAdsShow = false)
    }

    fun playContent(url:String, duration : Long){
        corePlayer.setMediaUri(uri = Uri.parse(url), vastTag = " " , isAdsShow = false, duration = duration)
    }

}