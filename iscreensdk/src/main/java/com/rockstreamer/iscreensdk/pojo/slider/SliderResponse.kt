package com.rockstreamer.iscreensdk.pojo.slider

import com.google.gson.annotations.SerializedName

open class SliderResponse (
    @SerializedName("id") val id : Long,
    @SerializedName("path") val path : String,
    @SerializedName("active") val active : Boolean,
    @SerializedName("contentId") val contentId: String,
    @SerializedName("premium") val premium: Boolean,
    @SerializedName("tvod") val tvod: Boolean,
    @SerializedName("position") val position : Int,
    @SerializedName("description") val description:String,
    @SerializedName("title") val title:String,
    @SerializedName("contentType") val contentType : String,
)