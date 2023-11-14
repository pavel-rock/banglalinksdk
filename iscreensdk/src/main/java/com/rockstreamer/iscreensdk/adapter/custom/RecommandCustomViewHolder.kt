package com.rockstreamer.iscreensdk.adapter.custom

import androidx.recyclerview.widget.RecyclerView
import com.rockstreamer.iscreensdk.databinding.ItemVideoRecommandBinding
import com.rockstreamer.iscreensdk.pojo.recommand.RecommendedResponse
import com.rockstreamer.iscreensdk.utils.BASE_CATEGORY_VIDEO_IMAGE
import com.rockstreamer.iscreensdk.utils.ImageLoader
import com.rockstreamer.iscreensdk.utils.gone
import com.rockstreamer.iscreensdk.utils.show

class RecommandCustomViewHolder(private val binding: ItemVideoRecommandBinding): RecyclerView.ViewHolder(binding.root) {
    var view = binding.root

    fun bind(response: RecommendedResponse){
        binding.contentTitle.text = response.title
        if (!response.horizontalThumbnails.isNullOrEmpty()){
            ImageLoader.loadVideoImage(view.context , BASE_CATEGORY_VIDEO_IMAGE +response.horizontalThumbnails[0].path , binding.videoThumbImage)
        }
        if (response.premium){
            binding.premiumLayout.imagePremium.show()
        }else{
            binding.premiumLayout.imagePremium.gone()
        }
    }
}