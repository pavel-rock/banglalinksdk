package com.rockstreamer.iscreensdk.activity

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.rockstreamer.iscreensdk.api.Status
import com.rockstreamer.iscreensdk.R
import com.rockstreamer.iscreensdk.activity.base.DetailsBaseActivity
import com.rockstreamer.iscreensdk.activity.base.onDeviceRotation
import com.rockstreamer.iscreensdk.adapter.SeriesAdapter
import com.rockstreamer.iscreensdk.databinding.SeriesDetailsActivityBinding
import com.rockstreamer.iscreensdk.listeners.OnSeriesCallBack
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import com.rockstreamer.iscreensdk.pojo.others.Genres
import com.rockstreamer.iscreensdk.pojo.series.EpisodeItem
import com.rockstreamer.iscreensdk.pojo.series.SeasonContent
import com.rockstreamer.iscreensdk.pojo.series.SeriesInfo
import com.rockstreamer.iscreensdk.utils.EqualSpacingItemDecoration
import com.rockstreamer.iscreensdk.utils.SERIES_CONTENT
import com.rockstreamer.iscreensdk.utils.SERIES_ID_PASS
import com.rockstreamer.iscreensdk.utils.StartSnapHelper
import com.rockstreamer.iscreensdk.utils.getSubscriptionInformation
import com.rockstreamer.iscreensdk.utils.gone
import com.rockstreamer.iscreensdk.utils.millisToFormattedDuration
import com.rockstreamer.iscreensdk.utils.registerOnSharedPreferenceChangedListener
import com.rockstreamer.iscreensdk.utils.show
import com.rockstreamer.iscreensdk.utils.unregisterOnSharedPreferenceChangedListener

class SeriesDetailsActivity : DetailsBaseActivity(), OnSeriesCallBack, onDeviceRotation, SharedPreferences.OnSharedPreferenceChangeListener{

    private var _binding : SeriesDetailsActivityBinding?=null
    private val binding get() = _binding!!

    lateinit var seriesAdapter: SeriesAdapter
    private lateinit var seriesInfo: SeriesInfo
    private lateinit var seasonContent: SeasonContent
    lateinit var episodeItem: EpisodeItem
    var episodeId: Int ?= null
    var seriesContentList: MutableList<EpisodeItem> = arrayListOf()
    var seriesIndex = 0

    private var isTrailerPlaying = false

    private var contentID:String?=null

    lateinit var argumentId : String

    override fun onStartDetails() {


    }

    override fun onStopDetails() {

    }

    override fun onDestroyDetails() {

    }
    lateinit var contentTitle: TextView

    companion object{
        var callback: oniScreenPremiumCallBack?=null
        fun setInterfaceInstance(callBack: oniScreenPremiumCallBack){
            this.callback = callBack
        }

    }


    override fun onCreateDetailsView(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        _binding = SeriesDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnDeviceRotationListener(this)

        setExoPlayer(binding.videoView, findViewById(R.id.exo_loading), nextButton = findViewById(R.id.next), previousButton= findViewById(R.id.previous))
        showNextPreviousController(value = true)
        contentTitle = findViewById(R.id.content_title)
        setupController(findViewById<LinearLayout>(R.id.linear_control))
        registerOnSharedPreferenceChangedListener(this)
        binding.premiumButton.setOnClickListener {

        }

        var argument = intent.getStringExtra(SERIES_ID_PASS)
        if (argument != null) {
            argumentId = argument
            seriesViewModel.getSeriesDetails(argument)
        }

        seriesAdapter = SeriesAdapter(this)

        binding.seriesRecycleview.layoutManager = GridLayoutManager(this, 2)
        binding.seriesRecycleview.adapter = seriesAdapter

        val snapHelperStart = StartSnapHelper()
        snapHelperStart.attachToRecyclerView(binding.seriesRecycleview)

        binding.seriesRecycleview.addItemDecoration(
            EqualSpacingItemDecoration(
                16,
                EqualSpacingItemDecoration.GRID
            )
        )

        seriesViewModel.seriesResponse.observe(this){
            when(it.status){

                Status.INVALIDTOKEN ->{
                    alertDialog.dismiss()
                    if (callback!=null){
                        callback!!.onTokenInvalid()
                    }
                }

                Status.LOADING ->{
                    alertDialog.show()
                }

                Status.SUCCESS ->{
                    alertDialog.dismiss()
                    seriesInfo = it.data!!.seriesInfo
                    displayVideoInfo(it.data.seriesInfo)

                    if (seriesInfo.premium){
                        if (getSubscriptionInformation().subscribe){
                            showSpinner(it.data.contents)
                        }else{
                            callback!!.onPremiumContentClick(context = this , contentId = "${it.data.seriesInfo.id}", type = SERIES_CONTENT)
                        }
                    }else{
                        showSpinner(it.data.contents)
                    }
                }
                Status.ERROR ->{
                    alertDialog.dismiss()
                }
            }
        }

        findViewById<ImageView>(R.id.image_back_arrow).setOnClickListener {
            onBackArrowPressed()
        }

        binding.layoutTrailer.setOnClickListener {
            isTrailerPlaying = true
            showNextPreviousController(value = false)
            seasonContent?.let {
                if (!it.trailerPath.isNullOrEmpty()){
                    playContent("${it.trailerPath}")
                    contentTitle.text = "${it.title} Trailer"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterOnSharedPreferenceChangedListener(this)
    }


    private fun onBackArrowPressed() {
        if (isFullScreen){
            fullScreenEnter(binding.videoView , binding.videoFrameLayout)
        } else {
            finish()
        }
    }

    override fun onNextClick() {

        nextSeries()
    }


    override fun onPreviousClick() {

        if (seriesIndex > 0){
            seriesIndex-=1
            playVideo(seriesContentList[seriesIndex])
        }
    }


    private fun nextSeries(){
        if (seriesIndex < seriesContentList.size-1){
            seriesIndex+=1
            playVideo(seriesContentList[seriesIndex])
        }
    }

    private fun showSpinner(seasonList: List<SeasonContent>){
        val seriesTitleList: MutableList<String> = ArrayList()
        seriesTitleList.clear()


        for (item in seasonList){
            seriesTitleList.add(item.title)
        }
        seasonContent = seasonList[0]
        episodeItem = seasonList[0].contents[0]
        playVideo(episodeItem)

        if (!seasonList[0].trailerPath.isNullOrEmpty()){
            binding.layoutTrailer.show()
        }else {
            binding.layoutTrailer.gone()
        }
        episodeId = episodeItem.id
        seriesContentList.clear()

        for (item in seasonList[0].contents){
            seriesContentList.add(item)
        }

        val coloredSpinner = findViewById<Spinner>(R.id.spinner)
        val aa: ArrayAdapter<*> = ArrayAdapter<Any?>(this,R.layout.color_spinner_layout,
            seriesTitleList as List<Any?>
        )
        aa.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        coloredSpinner.adapter = aa
        coloredSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                seriesAdapter.clearList()
                seriesContentList.clear()
                for (item in seasonList[position].contents) {
                    seriesContentList.add(item)
                }
                seriesAdapter.addAll(seasonList[position].contents as ArrayList<EpisodeItem> /* = java.util.ArrayList<com.pojo.series.EpisodeItem> */)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                seriesAdapter.addAll(seasonList[0].contents as ArrayList<EpisodeItem> /* = java.util.ArrayList<com.pojo.series.EpisodeItem> */)
            }
        }

    }

    private fun playVideo(episodeItem: EpisodeItem){
        showNextPreviousController(value = true)
        contentTitle.text = "${episodeItem.title}"
        contentID = "${episodeItem.id}"
        binding.nowPlaying.text = "Now Playing: ${episodeItem.title}"
        playContent("${episodeItem.path}")
        contentTitle.text = "${episodeItem.title}"
    }

    private fun displayVideoInfo(seriesInfo: SeriesInfo) {
        binding.videoTitle.text = seriesInfo.title
        showGeners(seriesInfo.genres as ArrayList<Genres>, binding.videoGener)
        showDetails(seriesInfo)
    }

    private fun showDetails(seriesInfo: SeriesInfo) {
        binding.textDetails.text = seriesInfo.description
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

    override fun onVideoEnd() {
        if (isTrailerPlaying){
            isTrailerPlaying = false
            if (seasonContent.premium){


            }else{
                playVideo(seriesContentList[0])
            }
        }else{
            nextSeries()
        }
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


    override fun onSeriesCallback(value: EpisodeItem, position: Int) {
        playVideo(value)
        episodeId = value.id
        episodeItem = value
        seriesIndex = position
        isTrailerPlaying = false
    }

    override fun onFullScreenButtonClick() {
        fullScreenEnter(binding.videoView , binding.videoFrameLayout)
    }

    override fun onExitFullScreenButtonClick() {

    }

    override fun onDeviceLeftSideRotate(): Boolean {
        screenRotateToLeftSide(binding.videoView , videoFrameLayout = binding.videoFrameLayout, isOffline = false)
        return true
    }

    override fun onDeviceRightSideRotate(): Boolean {
        screenRotateToRightSide(videoView = binding.videoView , videoFrameLayout = binding.videoFrameLayout, isOffline = false)
        return true
    }

    override fun onDownloadStart() {

    }

    override fun onDownloadFinished() {

    }

    override fun onDownloadError() {

    }

    override fun onAlreadyDownloaded(isDownload: Boolean) {

    }

    override fun onDownloadStops() {

    }

    override fun onDownloadPause() {

    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        seriesViewModel.getSeriesDetails(argumentId)
    }

}