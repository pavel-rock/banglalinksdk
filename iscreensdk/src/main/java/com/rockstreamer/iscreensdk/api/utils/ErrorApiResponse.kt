package com.rockstreamer.iscreensdk.api.utils

import com.google.gson.annotations.SerializedName


data class ErrorApiResponse (
    @SerializedName("message") val message : String
)