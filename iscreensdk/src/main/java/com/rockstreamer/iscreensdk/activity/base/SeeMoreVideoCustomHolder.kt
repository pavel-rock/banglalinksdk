package com.rockstreamer.iscreensdk.activity.base

import androidx.recyclerview.widget.RecyclerView
import com.rockstreamer.iscreensdk.databinding.ItemSeemoreVerticalBinding
import com.rockstreamer.iscreensdk.databinding.ItemSeemoreVideoBinding
import com.rockstreamer.iscreensdk.pojo.seemore.SeeMoreItems
import com.rockstreamer.iscreensdk.utils.AVATAR
import com.rockstreamer.iscreensdk.utils.BASE_CATEGORY_VIDEO_IMAGE
import com.rockstreamer.iscreensdk.utils.BASE_MOVIE_POSTER
import com.rockstreamer.iscreensdk.utils.ImageLoader
import com.rockstreamer.iscreensdk.utils.gone
import com.rockstreamer.iscreensdk.utils.show


class SeeMoreVideoCustomHolder(private val binding: ItemSeemoreVideoBinding):RecyclerView.ViewHolder(binding.root) {
    var view = binding.root
    var mainLayout = binding.mainLayout
    fun bind(seeMoreItems : SeeMoreItems){
        if (!seeMoreItems.horizontalThumbnails.isNullOrEmpty()){
            if (!seeMoreItems.horizontalThumbnails[0].path.isNullOrEmpty()){
                ImageLoader.loadVideoImage(context = view.context , url = BASE_CATEGORY_VIDEO_IMAGE+seeMoreItems.horizontalThumbnails[0].path , imageView = binding.videoThumbImage)
            } else {
                ImageLoader.loadVideoImage(context = view.context , url = AVATAR , imageView = binding.videoThumbImage)
            }
        }
        if (seeMoreItems.premium || seeMoreItems.tvod){
            binding.premiumLayout.imagePremium.show()
        } else {
            binding.premiumLayout.imagePremium.gone()
        }
    }
}

class SeeMoreVerticalVideoCustomHolder(private val binding: ItemSeemoreVerticalBinding):RecyclerView.ViewHolder(binding.root) {
    var view = binding.root
    var mainLayout = binding.mainLayout
    fun bind(seeMoreResponse: SeeMoreItems){
        binding.contentTitle.text = seeMoreResponse.title

        if (!seeMoreResponse.verticalThumbnails.isNullOrEmpty()){
            if (!seeMoreResponse.verticalThumbnails[0].path.isNullOrEmpty()){
                ImageLoader.loadMovieImage(context = view.context , url = BASE_MOVIE_POSTER+seeMoreResponse.verticalThumbnails[0].path , imageView = binding.videoThumbImage)
            } else {
                ImageLoader.loadMovieImage(context = view.context , url = AVATAR, imageView = binding.videoThumbImage)
            }
        }

        if (seeMoreResponse.premium || seeMoreResponse.tvod){
            binding.premiumLayout.imagePremium.show()
        } else {
            binding.premiumLayout.imagePremium.gone()
        }
    }
}


