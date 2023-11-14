package com.rockstreamer.iscreensdk.api.api

import com.rockstreamer.iscreensdk.pojo.details.VideoDetailsResponse
import com.rockstreamer.iscreensdk.pojo.seemore.SeeMoreResponse
import com.rockstreamer.iscreensdk.pojo.category.CategoryResponse
import com.rockstreamer.iscreensdk.pojo.recommand.RecommendedResponse
import com.rockstreamer.iscreensdk.pojo.series.SeriesResponse
import com.rockstreamer.iscreensdk.pojo.slider.SliderResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("/api/category")
    suspend fun getCategory(
        @Header("Authorization") token: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<CategoryResponse>

    @GET("/api/banner")
    suspend fun getSlider(
        @Header("Authorization") token: String,
        @Query("type") type: String
    ): Response<ArrayList<SliderResponse>>

    @GET("/api/v2/content/seemore/{id}")
    suspend fun getSeeMore(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Query("page") page: Int
    ): Response<SeeMoreResponse>


    @GET("/api/v2/content/series/{id}")
    suspend fun getSeriesDetails(
        @Header("Authorization") token: String,
        @Path("id") seriesId: String
    ): Response<SeriesResponse>


    @GET("/api/v2/content/{id}")
     suspend fun getVideoDetails(
        @Header("Authorization") token: String,
        @Path("id") videoID: String
    ): Response<VideoDetailsResponse>

    @GET("/api/content/suggestion?")
    suspend fun getRecommendedApi(
        @Header("Authorization") token: String,
        @Query("type") type: String
    ): Response<ArrayList<RecommendedResponse>>

}