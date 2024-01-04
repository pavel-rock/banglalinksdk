package com.rockstreamer.iscreensdk.pojo.seemore

import com.google.gson.annotations.SerializedName
import com.rockstreamer.iscreensdk.pojo.HorizontalThumbnails
import com.rockstreamer.iscreensdk.pojo.VerticalThumbnails
import com.rockstreamer.iscreensdk.pojo.others.Cast

data class SeeMoreItems(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("rating") val rating: String?,
    @SerializedName("durationInMinutes") val durationInMinutes: Int,
    @SerializedName("releaseDate") val releaseDate: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("path") val path: String?,
    @SerializedName("tvod") val tvod: Boolean,
    @SerializedName("casts") val casts: List<Cast>?,
    @SerializedName("viewCount") val viewCount: Int,
    @SerializedName("horizontalThumbnails") val horizontalThumbnails : List<HorizontalThumbnails>,
    @SerializedName("verticalThumbnails") val verticalThumbnails : List<VerticalThumbnails>,
    @SerializedName("premium") val premium: Boolean
)


data class SeeMoreResponse(	@SerializedName("orientationType") val orientationType : String,
                               @SerializedName("contents") val contents : SeeMoreContents
)


data class SeeMoreContents (
    @SerializedName("items") val items : List<SeeMoreItems>,
    @SerializedName("totalPage") val totalPage : Int,
    @SerializedName("page") val page : Int,
    @SerializedName("count") val count : Int
)
