package com.rockstreamer.iscreensdk.api.viewmodel.seemore

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rockstreamer.iscreensdk.api.repository.ContentRepository
import com.rockstreamer.iscreensdk.pojo.seemore.SeeMoreItems
import com.rockstreamer.iscreensdk.utils.API_TOKEN
import com.rockstreamer.iscreensdk.utils.loginState

class SeeMorePagingSource (private val contentRepository: ContentRepository, val id:String):
    PagingSource<Int, SeeMoreItems>() {

    override fun getRefreshKey(state: PagingState<Int, SeeMoreItems>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeeMoreItems> {

        return try {
            val position = params.key ?:1
            val response = contentRepository.getSeeMore(token = "${loginState.getString(API_TOKEN , "")}",id = id , position)
            LoadResult.Page(
                data = response.body()!!.contents.items.filter { !it.tvod },
                prevKey = if (position == 1) null else position - 1 ,
                nextKey = if (position < response.body()!!.contents.totalPage) response.body()!!.contents.page.plus(1) else null
            )
        } catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}