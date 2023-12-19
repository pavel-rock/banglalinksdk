package com.rockstreamer.iscreensdk.adapter.custom

import androidx.recyclerview.widget.RecyclerView
import com.rockstreamer.iscreensdk.databinding.ItemLayoutVideoBinding
import com.rockstreamer.iscreensdk.databinding.ItemVerticaleVideoBinding
import com.rockstreamer.iscreensdk.pojo.category.Contents
import com.rockstreamer.iscreensdk.utils.BASE_CATEGORY_VIDEO_IMAGE
import com.rockstreamer.iscreensdk.utils.BASE_MOVIE_POSTER
import com.rockstreamer.iscreensdk.utils.ImageLoader
import com.rockstreamer.iscreensdk.utils.gone
import com.rockstreamer.iscreensdk.utils.show


class CategoryVideoCustomViewHolder(private val binding: ItemLayoutVideoBinding): RecyclerView.ViewHolder(binding.root) {
    var view = binding.root

    fun bind(contents: Contents){
        binding.contentTitle.text = contents.title

        if (!contents.horizontalThumbnails.isNullOrEmpty()){
            ImageLoader.loadVideoImage(context = view.context , url = BASE_CATEGORY_VIDEO_IMAGE+contents.horizontalThumbnails[0].path , imageView = binding.videoThumbImage)
        }

        if (contents.premium){
            binding.premiumLayout.imagePremium.show()
        } else {
            binding.premiumLayout.imagePremium.gone()
        }
    }
}

class CategoryVerticalVideoCustomViewHolder(private val binding: ItemVerticaleVideoBinding): RecyclerView.ViewHolder(binding.root) {
    var view = binding.root

    fun bind(contents: Contents){
        binding.contentTitle.text = contents.title
        if (contents.verticalThumbnails.isNotEmpty()){
            ImageLoader.loadMovieImage(context = view.context , url = BASE_MOVIE_POSTER+contents.verticalThumbnails[0].path , imageView = binding.videoThumbImage)
        }

        if (contents.premium){
            binding.premiumLayout.imagePremium.show()
        } else {
            binding.premiumLayout.imagePremium.gone()
        }
    }
}



