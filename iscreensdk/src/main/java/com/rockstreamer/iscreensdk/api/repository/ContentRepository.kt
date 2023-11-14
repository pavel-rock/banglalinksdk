package com.rockstreamer.iscreensdk.api.repository

import com.google.gson.JsonObject
import com.rockstreamer.iscreensdk.api.api.ApiHelper
import com.rockstreamer.iscreensdk.api.repository.helper.ContentRepositoryHelper

import okhttp3.ResponseBody
import retrofit2.Response


class ContentRepository(private val apiHelper: ApiHelper) : ContentRepositoryHelper {
    override suspend fun getContentApi(token: String, type: String, page: Int) = apiHelper.getCategoryApi(type, page, token = token)
    override suspend fun getSlider(token: String, type: String) = apiHelper.getSlider(type, token = token)
    override suspend fun getSeeMore(token: String, id: String, page:Int) = apiHelper.getSeeMoreApi(id, page = page, token = token)
    override suspend fun getSeriesDetails(token: String, seriesId: String) = apiHelper.getSeriesDetails(seriesId, token = token)

    override suspend fun getVideoDetails(token: String, id: String) = apiHelper.getVideoDetails(id, token = token)
    override suspend fun getRecommandedContent(token: String, type: String) = apiHelper.getRecommandedContent(type, token = token)


    fun errorResponseManager(responseBody: ResponseBody):String{
//        val gson = Gson()
//        val type = object : TypeToken<ErrorApiResponse>() {}.type
//        var errorResponse: ErrorApiResponse? = gson.fromJson(responseBody.charStream(), type)
//        return if (errorResponse?.message==null){
//            "Something Went Wrong"
//        }else{
//            errorResponse.message
//        }
        return "Something Went Wrong"
    }
}