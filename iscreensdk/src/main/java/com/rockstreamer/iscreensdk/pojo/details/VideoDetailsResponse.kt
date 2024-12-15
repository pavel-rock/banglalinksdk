package com.rockstreamer.iscreensdk.pojo.details

import com.google.gson.annotations.SerializedName
import com.rockstreamer.iscreensdk.pojo.HorizontalThumbnails
import com.rockstreamer.iscreensdk.pojo.VerticalThumbnails
import com.rockstreamer.iscreensdk.pojo.series.Directors
import com.rockstreamer.iscreensdk.pojo.others.Artists
import com.rockstreamer.iscreensdk.pojo.others.Banner
import com.rockstreamer.iscreensdk.pojo.others.Cast
import com.rockstreamer.iscreensdk.pojo.others.Genres

data class VideoDetailsResponse(@SerializedName("id") val id: Long?,
                                @SerializedName("title") val title: String?,
                                @SerializedName("rating") val rating: String?,
                                @SerializedName("durationInSeconds") val durationInMinutes: Int,
                                @SerializedName("releaseDate") val releaseDate: String?,
                                @SerializedName("description") val description: String?,
                                @SerializedName("type") val type: String?,
                                @SerializedName("status") val status: String?,
                                @SerializedName("path") val path: String?,
                                @SerializedName("genres") val genres: ArrayList<Genres>,
                                @SerializedName("artists") val artists: ArrayList<Artists>,
                                @SerializedName("banners") val banners: ArrayList<Banner>,
                                @SerializedName("casts") val casts: List<Cast>,
                                @SerializedName("directors") val directors : List<Directors>,
                                @SerializedName("createdAt") val createdAt: String,
                                @SerializedName("trailer") val trailer: Boolean,
                                @SerializedName("horizontalThumbnails") val horizontalThumbnails : List<HorizontalThumbnails>,
                                @SerializedName("verticalThumbnails") val verticalThumbnails : List<VerticalThumbnails>,
                                @SerializedName("trailerPath") val trailerPath: String,
                                @SerializedName("restrictedCountry") val restricktedCountry: Boolean,
                                @SerializedName("live") val isLive: Boolean,
                                @SerializedName("tvod") val tvod: Boolean,
                                @SerializedName("premium") val premium: Boolean)