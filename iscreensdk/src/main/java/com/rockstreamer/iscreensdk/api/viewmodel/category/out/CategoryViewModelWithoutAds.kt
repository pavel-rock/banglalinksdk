package com.rockstreamer.iscreensdk.api.viewmodel.category.out

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rockstreamer.iscreensdk.api.repository.ContentRepository
import com.rockstreamer.iscreensdk.api.viewmodel.category.out.CategoryPagingSourceWithoutAds
import com.rockstreamer.iscreensdk.pojo.category.CategoryItems

class CategoryViewModelWithoutAds(private val contentRepository: ContentRepository):ViewModel(){
    fun getCategoryByType( type:String): LiveData<PagingData<CategoryItems>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                CategoryPagingSourceWithoutAds(contentRepository,type)
            }
            , initialKey = 1
        ).liveData
    }
}