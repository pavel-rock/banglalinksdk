package com.rockstreamer.iscreensdk.api.viewmodel.recommand

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rockstreamer.iscreensdk.api.NetworkHelper
import com.rockstreamer.iscreensdk.api.Resource
import com.rockstreamer.iscreensdk.api.repository.ContentRepository
import com.rockstreamer.iscreensdk.pojo.recommand.RecommendedResponse
import com.rockstreamer.iscreensdk.utils.API_TOKEN
import com.rockstreamer.iscreensdk.utils.loginState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecommandViewModel(private val contentRepository: ContentRepository, private val networkHelper: NetworkHelper):ViewModel() {

    private val _recommandResponse = MutableLiveData<Resource<ArrayList<RecommendedResponse>>>()
     val recommandResponse get() = _recommandResponse

    fun recommandApi(type:String){
        GlobalScope.launch {
            _recommandResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()){
                contentRepository.getRecommandedContent(token = "${loginState.getString(API_TOKEN , "")}",type).let {
                    if (it.isSuccessful){
                        _recommandResponse.postValue(Resource.success(it.body()!!.filter { !it.premium }) as Resource<ArrayList<RecommendedResponse>>?)
                    } else {
                        _recommandResponse.postValue(Resource.error(contentRepository.errorResponseManager(it.errorBody()!!), null))
                    }
                }
            } else _recommandResponse.postValue(Resource.error("No Internet Connection", null))
        }
    }

}