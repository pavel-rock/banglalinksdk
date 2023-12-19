package com.rockstreamer.iscreensdk.pojo.category

import com.google.gson.annotations.SerializedName
import com.rockstreamer.iscreensdk.pojo.HorizontalThumbnails
import com.rockstreamer.iscreensdk.pojo.VerticalThumbnails
import com.rockstreamer.iscreensdk.pojo.others.Genres

data class CategoryResponse(@SerializedName("items") val items : ArrayList<CategoryItems>,
                            @SerializedName("totalPage") val totalPage : Int,
                            @SerializedName("page") val page : Int,
                            @SerializedName("count") val count : Int)


data class CategoryItems(
    @SerializedName("id") val id: Long?,
    @SerializedName("title") val title: String?,
    @SerializedName("position") val position: Int,
    @SerializedName("type") val type:String?,
    @SerializedName("contents") val contents: ArrayList<Contents>,
    @SerializedName("orientationType") val imageOrientation:String?,
    @SerializedName("feature_contents") val feature_contents : List<FeatureContent>
)

data class Contents(

    @SerializedName("id") val id: Long?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("premium") val premium: Boolean,
    @SerializedName("thumbnail") val imageUrl: String?,
    @SerializedName("horizontalThumbnails") val horizontalThumbnails : List<HorizontalThumbnails>,
    @SerializedName("verticalThumbnails") val verticalThumbnails : List<VerticalThumbnails>,
    private var playState: Int = 0
)


data class FeatureContent(@SerializedName("id") val id: Int,
                          @SerializedName("title") val title: String?,
                          @SerializedName("status") val status: String?,
                          @SerializedName("type") val type: String?,
                          @SerializedName("thumbnail") val thumbnail: String?,
                          @SerializedName("position") val position: Int,
                          @SerializedName("genres") val genres: ArrayList<Genres>,
                          @SerializedName("description") val description: String?,
                          @SerializedName("path") val path: String?,
                          @SerializedName("rating") val rating: Int,
                          @SerializedName("releaseDate") val releaseDate: String?,
                          @SerializedName("durationInSeconds") val durationInSeconds: Int,
                          @SerializedName("horizontalThumbnails") val horizontalThumbnails : List<HorizontalThumbnails>,
                          @SerializedName("verticalThumbnails") val verticalThumbnails : List<VerticalThumbnails>,
                          @SerializedName("parentId") val parentId: String?,
                          @SerializedName("premium") val premium: Boolean)