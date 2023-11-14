package com.rockstreamer.iscreensdk.adapter.custom

import androidx.recyclerview.widget.RecyclerView
import com.rockstreamer.iscreensdk.databinding.ItemSeriesBinding
import com.rockstreamer.iscreensdk.pojo.series.EpisodeItem
import com.rockstreamer.iscreensdk.utils.BASE_CATEGORY_VIDEO_IMAGE
import com.rockstreamer.iscreensdk.utils.ImageLoader
import com.rockstreamer.iscreensdk.utils.gone
import com.rockstreamer.iscreensdk.utils.show

class SeriesCustomHolder(private val binding: ItemSeriesBinding):RecyclerView.ViewHolder(binding.root) {
    var view = binding.root
    var premiumLayout = binding.premiumLayout.imagePremium

    fun bind(episodeItem: EpisodeItem){
        binding.contentTitle.text = episodeItem.title
        //binding.content = EpisodeItem
        if (episodeItem.premium){
            premiumLayout.show()
        }else{
            premiumLayout.gone()
        }
        ImageLoader.loadVideoImage(view.context , BASE_CATEGORY_VIDEO_IMAGE +episodeItem.horizontalThumbnails[0].path , binding.videoThumbImage)
    }
}