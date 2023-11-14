package com.rockstreamer.iscreensdk.pojo.series

import HorizontalThumbnails
import VerticalThumbnails
import com.google.gson.annotations.SerializedName
import com.rockstreamer.iscreensdk.pojo.others.Cast
import com.rockstreamer.iscreensdk.pojo.others.Genres
import com.rockstreamer.iscreensdk.pojo.others.Thumbnail


data class SeriesResponse (

    @SerializedName("seriesInfo") val seriesInfo : SeriesInfo,
    @SerializedName("contents") val contents : List<SeasonContent>
)

data class SeriesInfo (
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("type") val type : String,
    @SerializedName("position") val position : Int,
    @SerializedName("genre") val genre : String,
    @SerializedName("description") val description : String,
    @SerializedName("releaseDate") val releaseDate : String,
    @SerializedName("thumbnails") val thumbnails : List<Thumbnail>,
    @SerializedName("genres") val genres : List<Genres>,
    @SerializedName("directors") val directors : List<Directors>,
    @SerializedName("casts") val casts : List<Cast>,
    @SerializedName("path") val path : String,
    @SerializedName("rating") val rating : Int,
    @SerializedName("durationInSeconds") val durationInSeconds : Int,
    @SerializedName("parentId") val parentId : String,
    @SerializedName("subtitles") val subtitles : List<String>,
    @SerializedName("seasonCount") val seasonCount : Int,
    @SerializedName("episodeCount") val episodeCount : Int,
    @SerializedName("restrictedCountry") val restrictedCountry : Boolean,
    @SerializedName("premium") val premium : Boolean,
    @SerializedName("createdAt") var createdAt: String?,
    @SerializedName("private") val private : Boolean,
    @SerializedName("trailer") val trailer : Boolean,
    @SerializedName("trailerPath") val trailerPath : String,

    )


data class Directors (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("bio") val bio : String,
    @SerializedName("birthDate") val birthDate : String,
    @SerializedName("profileActive") val profileActive : Boolean,
    @SerializedName("path") val path : String
)





data class SeasonContent (

    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("type") val type : String,
    @SerializedName("position") val position : Int,
    @SerializedName("genre") val genre : String,
    @SerializedName("description") val description : String,
    @SerializedName("releaseDate") val releaseDate : String,
    @SerializedName("thumbnails") val thumbnails : List<Thumbnail>,
    @SerializedName("casts") val casts : List<Cast>,
    @SerializedName("directors") val directors : List<String>,
    @SerializedName("genres") val genres : List<Genres>,
    @SerializedName("path") val path : String,
    @SerializedName("rating") val rating : Int,
    @SerializedName("durationInSeconds") val durationInSeconds : Int,
    @SerializedName("parentId") val parentId : String,
    @SerializedName("subtitles") val subtitles : List<String>,
    @SerializedName("seasonCount") val seasonCount : String,
    @SerializedName("episodeCount") val episodeCount : Int,
    @SerializedName("contents") val contents : List<EpisodeItem>,
    @SerializedName("restrictedCountry") val restrictedCountry : Boolean,
    @SerializedName("premium") val premium : Boolean,
    @SerializedName("private") val private : Boolean,
    @SerializedName("trailer") val trailer : Boolean,
    @SerializedName("trailerPath") val trailerPath : String
)

data class EpisodeItem (

    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("type") val type : String,
    @SerializedName("position") val position : Int,
    @SerializedName("description") val description : String,
    @SerializedName("releaseDate") val releaseDate : String,
    @SerializedName("casts") val casts : List<Cast>,
    @SerializedName("genres") val genres : List<Genres>,
    @SerializedName("path") val path : String,
    @SerializedName("directors") val directors : List<Directors>,
    @SerializedName("rating") val rating : Int,
    @SerializedName("durationInSeconds") val durationInSeconds : Int,
    @SerializedName("parentId") val parentId : String,
    @SerializedName("subtitles") val subtitles : List<String>,
    @SerializedName("seasonCount") val seasonCount : String,
    @SerializedName("episodeCount") val episodeCount : Int,
    @SerializedName("restrictedCountry") val restrictedCountry : Boolean,
    @SerializedName("premium") val premium : Boolean,
    @SerializedName("createdAt") var createdAt: String?,
    @SerializedName("horizontalThumbnails") val horizontalThumbnails : List<HorizontalThumbnails>,
    @SerializedName("verticalThumbnails") val verticalThumbnails : List<VerticalThumbnails>,
    @SerializedName("private") val private : Boolean
)
