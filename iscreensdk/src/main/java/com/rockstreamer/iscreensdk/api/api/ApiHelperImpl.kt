package com.rockstreamer.iscreensdk.api.api

import com.rockstreamer.iscreensdk.api.api.ApiHelper
import com.rockstreamer.iscreensdk.api.api.ApiService

class ApiHelperImpl(private val apiService: ApiService): ApiHelper {
    override suspend fun getCategoryApi(type: String, page: Int, token: String) = apiService.getCategory(token = "Bearer $token",type , page)
    override suspend fun getSlider(type: String, token: String) = apiService.getSlider(token = "Bearer $token"  ,type)
    override suspend fun getSeeMoreApi(id: String, page:Int, token: String) = apiService.getSeeMore(token = "Bearer $token" , id, page = page)
    override suspend fun getSeriesDetails(seriesId: String, token: String) = apiService.getSeriesDetails(token = "Bearer $token", seriesId)
    override suspend fun getVideoDetails(id: String, token: String) = apiService.getVideoDetails(token = "Bearer $token", id)
    override suspend fun getRecommandedContent(type: String, token: String) = apiService.getRecommendedApi(token = "Bearer $token", type)
}