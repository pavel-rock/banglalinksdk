package com.rockstreamer.iscreensdk.pojo.others

import com.google.gson.annotations.SerializedName

data class Genres(@SerializedName("id") val id: Long?,
                         @SerializedName("name") val name: String?
)