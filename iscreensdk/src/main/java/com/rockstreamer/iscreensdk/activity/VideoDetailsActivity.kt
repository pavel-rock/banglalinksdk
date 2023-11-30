package com.rockstreamer.iscreensdk.activity

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rockstreamer.iscreensdk.api.Status
import com.rockstreamer.iscreensdk.R
import com.rockstreamer.iscreensdk.activity.base.DetailsBaseActivity
import com.rockstreamer.iscreensdk.activity.base.onDeviceRotation
import com.rockstreamer.iscreensdk.adapter.RecommandAdapter
import com.rockstreamer.iscreensdk.databinding.ActivityVideoDetailsBinding
import com.rockstreamer.iscreensdk.listeners.OnRecommandCallback
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import com.rockstreamer.iscreensdk.pojo.details.VideoDetailsResponse
import com.rockstreamer.iscreensdk.pojo.recommand.RecommendedResponse
import com.rockstreamer.iscreensdk.utils.EqualSpacingItemDecoration
import com.rockstreamer.iscreensdk.utils.StartSnapHelper
import com.rockstreamer.iscreensdk.utils.VIDEO_CONTENT
import com.rockstreamer.iscreensdk.utils.VIDEO_ID_PASS
import com.rockstreamer.iscreensdk.utils.getSubscriptionInformation
import com.rockstreamer.iscreensdk.utils.gone
import com.rockstreamer.iscreensdk.utils.millisToFormattedDuration
import com.rockstreamer.iscreensdk.utils.processCast
import com.rockstreamer.iscreensdk.utils.processDirector
import com.rockstreamer.iscreensdk.utils.registerOnSharedPreferenceChangedListener
import com.rockstreamer.iscreensdk.utils.show
import com.rockstreamer.iscreensdk.utils.unregisterOnSharedPreferenceChangedListener

class VideoDetailsActivity : DetailsBaseActivity(), OnRecommandCallback, onDeviceRotation , SharedPreferences.OnSharedPreferenceChangeListener{

    private var _binding: ActivityVideoDetailsBinding?=null
    private val binding get() = _binding!!


    private var videoDetailsResponse: VideoDetailsResponse?=null
    var recommondedAdapter: RecommandAdapter?=null
    var linearLayoutManagerRecommanded : LinearLayoutManager?=null

    private var trailerLink:String?=null
    private var contentID:String?=null
    private var isLive: Boolean = false
    lateinit var contentTitle: TextView

    lateinit var argumentId : String

    companion object{
        var callback: oniScreenPremiumCallBack?=null
        fun setInterfaceInstance(callBack: oniScreenPremiumCallBack){
            this.callback = callBack
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterOnSharedPreferenceChangedListener(this)
    }

    override fun onCreateDetailsView(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        _binding = ActivityVideoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setExoPlayer(binding.videoView, findViewById(R.id.exo_loading), findViewById(R.id.next), findViewById(R.id.previous))
        contentTitle = findViewById(R.id.content_title)
        setOnDeviceRotationListener(this)
        setupController(findViewById<LinearLayout>(R.id.linear_control))
        registerOnSharedPreferenceChangedListener(this)
        binding.premiumButton.setOnClickListener {

        }

        val actionBar = supportActionBar
        actionBar?.hide()

        var argument = intent.getStringExtra(VIDEO_ID_PASS)
        if (argument != null) {
            argumentId = argument
            videoDetailsViewModel.getVideoDetails(argument)
            recommandViewModel.recommandApi("video")
        }
        recommondedAdapter = RecommandAdapter(this)
        binding.recommendedRecycleview.layoutManager = GridLayoutManager(this, 2)
        val snapHelperStart = StartSnapHelper()
        snapHelperStart.attachToRecyclerView(binding.recommendedRecycleview)
        binding.recommendedRecycleview.adapter = recommondedAdapter

        binding.recommendedRecycleview.addItemDecoration(
            EqualSpacingItemDecoration(
                16,
                EqualSpacingItemDecoration.GRID
            )
        )

        linearLayoutManagerRecommanded = LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL, false)

        videoDetailsViewModel.videoResponse.observe(this){
            when(it.status){

                Status.INVALIDTOKEN->{
                    alertDialog.dismiss()
                    if (callback!=null){
                        callback?.onTokenInvalid()
                    }
                }

                Status.LOADING ->{
                    alertDialog.show()
                }

                Status.SUCCESS ->{
                    alertDialog.dismiss()
                    displayVideoInfo(it.data!!)
                    videoDetailsResponse = it.data

                    if (it.data.premium){
                        if (getSubscriptionInformation().subscribe){
                            playVideo(videoDetailsResponse!!)
                        }else{
                            if (callback !=null){
                                callback?.onPremiumContentClick(context = this, contentId = "${videoDetailsResponse!!.id}", type = VIDEO_CONTENT )
                            }
                        }
                    }else{
                        playVideo(videoDetailsResponse!!)
                    }
                }
                Status.ERROR ->{
                    alertDialog.dismiss()
                    //showErrorToast("${it.message}")
                }
            }
        }

        recommandViewModel.recommandResponse.observe(this){
            when(it.status){


                Status.INVALIDTOKEN ->{

                }

                Status.LOADING ->{
                    binding.recommendedProgressbar.show()
                }

                Status.SUCCESS ->{
                    binding.recommendedProgressbar.gone()
                    it.data?.let { it1 -> recommondedAdapter!!.addAll(it1) }
                }

                Status.ERROR ->{
                    binding.recommendedProgressbar.gone()
                    //showErrorToast(it.message.toString())
                }
            }
        }


        binding.layoutTrailer.setOnClickListener {
            if (!trailerLink.isNullOrBlank()){
                corePlayer.setMediaUri(uri = Uri.parse(trailerLink), vastTag = "" , isAdsShow = false)
            }
        }

        findViewById<ImageView>(R.id.image_back_arrow).setOnClickListener {
            onBackArrowPressed()
        }
    }

    private fun onBackArrowPressed(){
        if (isFullScreen){
            fullScreenEnter(binding.videoView , binding.videoFrameLayout)
        } else {
            finish()
        }
    }

    override fun onStartDetails() {

    }


    override fun onStart() {
        super.onStart()

    }

    override fun onStopDetails() {

    }

    override fun onDestroyDetails() {

    }

    private fun showPremiumBanner(premiumContent:Boolean){
//        if (premiumContent){
//            if (PreferenceUtil.isSubscribed){
//                binding.premiumButton.gone()
//            }else{
//                binding.premiumButton.show()
//            }
//        }else {
//            binding.premiumButton.gone()
//        }
    }


    override fun onVideoEnd() {

    }

    override fun onIdle() {

    }


    override fun onReady() {
        binding.videoDuration.text = corePlayer.duration.millisToFormattedDuration()
    }

    override fun onVideoProgressUpdate(duration: Long) {

    }

    override fun onImaAdsShow(value: Boolean) {

    }

    private fun playVideo(videoDetailsResponse: VideoDetailsResponse){
        contentID = "${videoDetailsResponse.id}"
        contentTitle.text = "${videoDetailsResponse.title}"

        if (videoDetailsResponse.premium){
            if (videoDetailsResponse.trailer){
                playContent("${videoDetailsResponse.trailerPath}")
                contentTitle.text = "${videoDetailsResponse.title} Trailer"
            }
        }else{
            playContent("${videoDetailsResponse.path}")
        }
    }



    @SuppressLint("SetTextI18n")
    private fun displayVideoInfo(videoDetailsResponse: VideoDetailsResponse) {

        if (videoDetailsResponse.trailer){
            trailerLink = videoDetailsResponse.trailerPath
            binding.layoutTrailer.show()
        }else{
            binding.layoutTrailer.gone()
        }

        binding.videoTitle.text = videoDetailsResponse.title
        showGeners(videoDetailsResponse.genres, binding.videoGener)
        showDetails(videoDetailsResponse)

        showPremiumBanner(videoDetailsResponse.premium)
        binding.textCast.text = "Cast: ${processCast(videoDetailsResponse.casts)}"
        binding.textDirector.text = "Directors: ${processDirector(videoDetailsResponse.directors)}"
    }

    private fun showDetails(videoDetailsResponse: VideoDetailsResponse) {
        binding.textDetails.text = videoDetailsResponse.description
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onBackPressed() {
        if (isFullScreen){
            fullScreenEnter(binding.videoView , binding.videoFrameLayout)
        } else{
            finish()
            super.onBackPressed()
        }
    }

    override fun onRecommandClick(response: RecommendedResponse) {
        videoDetailsViewModel.getVideoDetails(response.id.toString())
        recommandViewModel.recommandApi("video")
    }


    override fun onFullScreenButtonClick() {
        fullScreenEnter(binding.videoView ,binding.videoFrameLayout)
    }

    override fun onExitFullScreenButtonClick() {

    }

    override fun onDeviceLeftSideRotate(): Boolean {
        screenRotateToLeftSide(binding.videoView , videoFrameLayout = binding.videoFrameLayout, isOffline = false)
        return true
    }

    override fun onDeviceRightSideRotate():Boolean {
        screenRotateToRightSide(videoView = binding.videoView , videoFrameLayout = binding.videoFrameLayout, isOffline = false)
        return true
    }

    override fun onDownloadStart() {

    }

    override fun onDownloadFinished() {

    }


    override fun onDownloadError() {

    }

    override fun onAlreadyDownloaded(value: Boolean) {

    }

    override fun onDownloadStops() {


    }

    override fun onNextClick() {

    }

    override fun onPreviousClick() {

    }

    override fun onDownloadPause() {

    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        videoDetailsViewModel.getVideoDetails(argumentId)
        recommandViewModel.recommandApi("video")
    }
}