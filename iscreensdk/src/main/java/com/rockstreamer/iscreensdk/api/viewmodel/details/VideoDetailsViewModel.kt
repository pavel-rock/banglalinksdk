package com.rockstreamer.iscreensdk.api.viewmodel.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rockstreamer.iscreensdk.api.NetworkHelper
import com.rockstreamer.iscreensdk.api.Resource
import com.rockstreamer.iscreensdk.api.repository.ContentRepository
import com.rockstreamer.iscreensdk.pojo.details.VideoDetailsResponse
import com.rockstreamer.iscreensdk.utils.API_TOKEN
import com.rockstreamer.iscreensdk.utils.loginState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VideoDetailsViewModel(private val contentRepository: ContentRepository, private val networkHelper: NetworkHelper):ViewModel() {
    private val _videoResponse  = MutableLiveData<Resource<VideoDetailsResponse>>()
    val videoResponse get() = _videoResponse

    fun getVideoDetails(id:String){
        GlobalScope.launch {
            _videoResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()){
                contentRepository.getVideoDetails(token = "${loginState.getString(API_TOKEN , "")}",id).let {
                    if (it.isSuccessful){
                        _videoResponse.postValue(Resource.success(it.body()))
                    } else {
                        if (it.code() == 403 || it.code() ==401){
                            _videoResponse.postValue(Resource.invalidToken(null))
                        }else{
                            _videoResponse.postValue(Resource.error(contentRepository.errorResponseManager(it.errorBody()!!), null))
                        }
                    }
                }
            } else _videoResponse.postValue(Resource.error("No Internet Connection", null))
        }
    }
}