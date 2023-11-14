package com.rockstreamer.iscreensdk.api.api

import com.rockstreamer.iscreensdk.pojo.details.VideoDetailsResponse
import com.rockstreamer.iscreensdk.pojo.seemore.SeeMoreResponse
import com.rockstreamer.iscreensdk.pojo.category.CategoryResponse
import com.rockstreamer.iscreensdk.pojo.recommand.RecommendedResponse
import com.rockstreamer.iscreensdk.pojo.series.SeriesResponse
import com.rockstreamer.iscreensdk.pojo.slider.SliderResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getCategoryApi(type:String , page:Int, token: String):Response<CategoryResponse>
    suspend fun getSlider(type:String, token: String):Response<ArrayList<SliderResponse>>
    suspend fun getSeeMoreApi(id:String, page:Int, token: String):Response<SeeMoreResponse>
    suspend fun getSeriesDetails(seriesId:String, token: String):Response<SeriesResponse>
    suspend fun getVideoDetails(id:String, token: String):Response<VideoDetailsResponse>
    suspend fun getRecommandedContent(type:String, token: String):Response<ArrayList<RecommendedResponse>>
}