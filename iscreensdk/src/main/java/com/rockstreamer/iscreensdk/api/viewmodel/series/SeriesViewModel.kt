package com.rockstreamer.iscreensdk.api.viewmodel.series

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rockstreamer.iscreensdk.api.NetworkHelper
import com.rockstreamer.iscreensdk.api.Resource
import com.rockstreamer.iscreensdk.api.repository.ContentRepository
import com.rockstreamer.iscreensdk.pojo.series.SeriesResponse
import com.rockstreamer.iscreensdk.utils.API_TOKEN
import com.rockstreamer.iscreensdk.utils.loginState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SeriesViewModel(private val contentRepository: ContentRepository, private val networkHelper: NetworkHelper):ViewModel() {
    private val _seriesResponse = MutableLiveData<Resource<SeriesResponse>>()
    val seriesResponse get() = _seriesResponse

    fun getSeriesDetails(seriesId:String){
        GlobalScope.launch {
            _seriesResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()){
                contentRepository.getSeriesDetails(token = "${loginState.getString(API_TOKEN , "")}",seriesId).let {
                    if (it.isSuccessful){
                        _seriesResponse.postValue(Resource.success(it.body()))
                    } else {
                        if (it.code() == 403 || it.code() ==401){
                            _seriesResponse.postValue(Resource.invalidToken(null))
                        }else{
                            _seriesResponse.postValue(Resource.error(contentRepository.errorResponseManager(it.errorBody()!!), null))
                        }
                    }
                }
            } else _seriesResponse.postValue(Resource.error("No Internet Connection", null))
        }
    }
}