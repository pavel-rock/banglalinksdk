package com.rockstreamer.iscreensdk

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rockstreamer.iscreensdk.api.Status
import com.rockstreamer.iscreensdk.adapter.CategoryMainAdapterWithoutAds
import com.rockstreamer.iscreensdk.adapter.SliderAdapter
import com.rockstreamer.iscreensdk.activity.base.BaseActivity
import com.rockstreamer.iscreensdk.databinding.IscreenActivityBinding
import com.rockstreamer.iscreensdk.listeners.OnCategoryCallback
import com.rockstreamer.iscreensdk.listeners.onBannerCallback
import com.rockstreamer.iscreensdk.listeners.oniScreenPremiumCallBack
import com.rockstreamer.iscreensdk.pojo.category.Contents
import com.rockstreamer.iscreensdk.pojo.category.FeatureContent
import com.rockstreamer.iscreensdk.pojo.category.SortByBannerPosition
import com.rockstreamer.iscreensdk.pojo.slider.SliderResponse
import com.rockstreamer.iscreensdk.utils.SERIES_CONTENT
import com.rockstreamer.iscreensdk.utils.VIDEO_CONTENT
import com.rockstreamer.iscreensdk.utils.getSubscriptionInformation
import com.rockstreamer.iscreensdk.utils.gone
import com.rockstreamer.iscreensdk.utils.openDetailsScreen
import com.rockstreamer.iscreensdk.utils.openSeeMoreDetails
import com.rockstreamer.iscreensdk.utils.registerOnSharedPreferenceChangedListener
import com.rockstreamer.iscreensdk.utils.unregisterOnSharedPreferenceChangedListener
import kotlinx.coroutines.launch
import java.util.Collections

class IScreenActivity : BaseActivity() , onBannerCallback, OnCategoryCallback, oniScreenPremiumCallBack , SharedPreferences.OnSharedPreferenceChangeListener{

    private lateinit var binding: IscreenActivityBinding


    companion object{
        var callback: oniScreenPremiumCallBack?=null
        var context: Context ?= null
        fun setInterfaceInstance(callBack: oniScreenPremiumCallBack){
            this.callback = callBack
        }

        fun stopiScreen(){
            if (context!=null){
                (context as Activity).finish()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterOnSharedPreferenceChangedListener(this)
    }


    override fun onCreateActivity() {
        binding = IscreenActivityBinding.inflate(layoutInflater)
        context = this
        setContentView(binding.root)
        categoryMainAdapterWithAds = CategoryMainAdapterWithoutAds(this)
        binding.categoryRecycleview.layoutManager = LinearLayoutManager(this)
        binding.categoryRecycleview.adapter = categoryMainAdapterWithAds
        binding.idToolbar.toolbarTitle.text = "iScreen"
        registerOnSharedPreferenceChangedListener(this)

        binding.idToolbar.toolbarBack.setOnClickListener {
            finish()
        }

        sliderViewModel.sliderResponse.observe(this){
            when(it.status){
                Status.LOADING ->{

                }
                Status.ERROR ->{

                }

                Status.INVALIDTOKEN ->{
                    callback?.onTokenInvalid()
                }

                Status.SUCCESS ->{
                    Collections.sort(it.data, SortByBannerPosition())
                    binding.viewPager.adapter = it.data?.let { it1 ->

                        SliderAdapter(this ,
                            it1, this)
                    }
                    lifecycleScope.launch {
                        categoryViewModelWithAds.getCategoryByType("video").observe(this@IScreenActivity){
                            it?.let {
                                binding.categoryProgressbar.gone()
                                categoryMainAdapterWithAds.submitData(lifecycle, it)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onBannerClick(item: SliderResponse) {
        when(item.contentType){
            "video" ->{
                if (item.premium){
                    if (getSubscriptionInformation().subscribe){
                        openDetailsScreen(item.contentId, "video", this)
                    }else{
                        callback?.onPremiumContentClick(context = this, contentId = "${item.contentId}", type = "video")
                    }
                }else{
                    openDetailsScreen(item.contentId, "video", this)
                }
            }
            "series" ->{
                if (item.premium){
                    if (getSubscriptionInformation().subscribe){
                        openDetailsScreen(item.contentId, "series", this)
                    }else{
                        callback?.onPremiumContentClick(context = this, contentId = "${item.contentId}", type = "series")
                    }
                }else{
                    openDetailsScreen(item.contentId, "series", this)
                }
            }
        }
    }

    override fun onCategorySeeMoreCallback(
        id: String,
        title: String,
        type: String,
        imageType: String
    ) {
        openSeeMoreDetails(id = id , title = title ,imageType = imageType, context = this )
    }

    override fun onCategoryChildClickCallback(contents: Contents) {
        if (contents.premium){
            if (getSubscriptionInformation().subscribe){
                openDetailsScreen("${contents.id}", type = "${contents.type}", this)
            }else{
                callback?.onPremiumContentClick(context = this, contentId = "${contents.id}", type = "${contents.type}" )
            }
        }else{
            openDetailsScreen("${contents.id}", type = "${contents.type}", this)
            //Log.d("APP_STATUS", "${contents.type} : ${contents.id}")
        }

    }

    override fun onFeatureContentClick(featureContent: FeatureContent) {

    }

    override fun onPremiumContentClick(context: Context, contentId: String, type: String) {
        callback?.onPremiumContentClick(this, contentId = contentId , type = type)
    }

    override fun onTokenInvalid() {

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, p1: String?) {
        sliderViewModel.retrySliderApi()
    }
}