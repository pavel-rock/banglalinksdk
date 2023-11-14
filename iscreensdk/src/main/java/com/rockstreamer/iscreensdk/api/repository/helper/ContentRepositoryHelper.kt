package com.rockstreamer.iscreensdk.api.repository.helper


import com.rockstreamer.iscreensdk.pojo.details.VideoDetailsResponse
import com.rockstreamer.iscreensdk.pojo.seemore.SeeMoreResponse
import com.rockstreamer.iscreensdk.pojo.category.CategoryResponse
import com.rockstreamer.iscreensdk.pojo.recommand.RecommendedResponse
import com.rockstreamer.iscreensdk.pojo.series.SeriesResponse
import com.rockstreamer.iscreensdk.pojo.slider.SliderResponse
import retrofit2.Response

interface ContentRepositoryHelper {
    suspend fun getContentApi(token: String, type:String , page:Int):Response<CategoryResponse>
    suspend fun getSlider(token: String, type:String):Response<ArrayList<SliderResponse>>
    suspend fun getSeeMore(token: String, id:String,page:Int):Response<SeeMoreResponse>
    suspend fun getSeriesDetails(token: String, seriesId:String):Response<SeriesResponse>
    suspend fun getVideoDetails(token: String, id:String):Response<VideoDetailsResponse>
    suspend fun getRecommandedContent(token: String, type: String):Response<ArrayList<RecommendedResponse>>
}