package com.rockstreamer.iscreensdk.api.viewmodel.seemore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rockstreamer.iscreensdk.api.repository.ContentRepository
import com.rockstreamer.iscreensdk.api.viewmodel.seemore.SeeMorePagingSource
import com.rockstreamer.iscreensdk.pojo.seemore.SeeMoreItems

//class SeeMoreViewModel(private val contentRepository: ContentRepository, private val networkHelper: NetworkHelper):ViewModel() {
//
//    private val _videoSeeMoreResponse = MutableLiveData<Resource<ArrayList<SeeMoreResponse>>>()
//    val videoSeeMoreResponse get() = _videoSeeMoreResponse
//
//    fun videoSeeMoreApiCall(id:String){
//        GlobalScope.launch {
//            _videoSeeMoreResponse.postValue(Resource.loading(null))
//            if (networkHelper.isNetworkConnected()){
//                contentRepository.getSeeMore(id).let {
//                    if (it.isSuccessful){
//                        _videoSeeMoreResponse.postValue(Resource.success(it.body()))
//                    }else _videoSeeMoreResponse.postValue(Resource.error(it.errorBody().toString(), null))
//                }
//            } else _videoSeeMoreResponse.postValue(Resource.error("No Internet Connection", null))
//
//        }
//    }
//}


class SeeMoreViewModel(private val contentRepository: ContentRepository):ViewModel(){
    fun getSeeMore(id:String): LiveData<PagingData<SeeMoreItems>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                SeeMorePagingSource(contentRepository,id)
            }
            , initialKey = 1
        ).liveData
    }
}
