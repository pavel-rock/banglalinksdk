package com.rockstreamer.iscreensdk.api.viewmodel.category.out

import android.annotation.SuppressLint
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rockstreamer.iscreensdk.api.repository.ContentRepository
import com.rockstreamer.iscreensdk.pojo.category.CategoryItems
import com.rockstreamer.iscreensdk.pojo.category.SortByPosition
import com.rockstreamer.iscreensdk.utils.API_TOKEN
import com.rockstreamer.iscreensdk.utils.loginState
import java.util.Collections

const val BANNER_ADS_INTERVAL = 4

class CategoryPagingSourceWithoutAds (private val contentRepository: ContentRepository, val type:String):PagingSource<Int, CategoryItems>() {

    override fun getRefreshKey(state: PagingState<Int, CategoryItems>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    @SuppressLint("VisibleForTests")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CategoryItems> {
        return try {
            val position = params.key ?:1
            val response = contentRepository.getContentApi(token = "${loginState.getString(API_TOKEN , "")}", type = type , position)
            Collections.sort(response.body()!!.items, SortByPosition())

            LoadResult.Page(
                data = response.body()!!.items,
                prevKey = if (position == 1) null else position - 1 ,
                nextKey = if (position < response.body()!!.totalPage) response.body()!!.page.plus(1) else null
            )
        } catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}