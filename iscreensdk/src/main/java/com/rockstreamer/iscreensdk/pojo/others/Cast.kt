package com.rockstreamer.iscreensdk.pojo.others

import com.google.gson.annotations.SerializedName

data class Cast(@SerializedName("id") val id: Int,
                @SerializedName("name") val name: String?,
                @SerializedName("bio") val bio: String?,
                @SerializedName("birthDate") val brithday: String?)