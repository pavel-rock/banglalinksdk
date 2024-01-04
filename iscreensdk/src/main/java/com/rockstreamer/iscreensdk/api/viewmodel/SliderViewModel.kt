package com.rockstreamer.iscreensdk.api.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rockstreamer.iscreensdk.api.NetworkHelper
import com.rockstreamer.iscreensdk.api.Resource
import com.rockstreamer.iscreensdk.api.repository.ContentRepository
import com.rockstreamer.iscreensdk.pojo.slider.SliderResponse
import com.rockstreamer.iscreensdk.utils.API_TOKEN
import com.rockstreamer.iscreensdk.utils.loginState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class SliderViewModel(private val contentRepository: ContentRepository, private val networkHelper: NetworkHelper):ViewModel() {
    private val _sliderResponse = MutableLiveData<Resource<ArrayList<SliderResponse>>>()
     val sliderResponse get() = _sliderResponse

    init {
        sliderApiCall()
    }

    fun retrySliderApi(){
        sliderApiCall()
    }

    private fun sliderApiCall(){
        GlobalScope.launch {
            _sliderResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()){
                contentRepository.getSlider(token = "${loginState.getString(API_TOKEN , "")}","web").let {
                    if (it.isSuccessful){
                        _sliderResponse.postValue(Resource.success(it.body()?.filter { !it.tvod }) as Resource<ArrayList<SliderResponse>>?)
                    }else {
                        _sliderResponse.postValue(Resource.invalidToken(null))
                    }
                }
            } else _sliderResponse.postValue(Resource.error("No Internet Connection" , null))
        }
    }
}