package com.rockstreamer.iscreensdk.pojo.recommand

import com.google.gson.annotations.SerializedName
import com.rockstreamer.iscreensdk.pojo.HorizontalThumbnails

data class RecommendedResponse(@SerializedName("id") val id : Int,
                               @SerializedName("title") val title : String,
                               @SerializedName("status") val status : String,
                               @SerializedName("type") val type : String,
                               @SerializedName("thumbnail") val thumbnail : String,
                               @SerializedName("horizontalThumbnails") val horizontalThumbnails : List<HorizontalThumbnails>,
                               @SerializedName("premium") val premium : Boolean)