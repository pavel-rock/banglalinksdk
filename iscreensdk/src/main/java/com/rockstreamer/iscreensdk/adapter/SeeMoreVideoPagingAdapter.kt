package com.rockstreamer.iscreensdk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rockstreamer.iscreensdk.R
import com.rockstreamer.iscreensdk.activity.base.SeeMoreVerticalVideoCustomHolder
import com.rockstreamer.iscreensdk.activity.base.SeeMoreVideoCustomHolder
import com.rockstreamer.iscreensdk.databinding.ItemSeemoreVerticalBinding
import com.rockstreamer.iscreensdk.databinding.ItemSeemoreVideoBinding
import com.rockstreamer.iscreensdk.listeners.OnSeeMoreContentListener
import com.rockstreamer.iscreensdk.pojo.seemore.SeeMoreItems
import com.rockstreamer.iscreensdk.utils.IMAGE_HORIZONTAL
import com.rockstreamer.iscreensdk.utils.IMAGE_VERTICAL
import com.rockstreamer.iscreensdk.utils.SERIES_CONTENT
import com.rockstreamer.iscreensdk.utils.VIDEO_CONTENT

class SeeMoreVideoPagingAdapter(private val callBack: OnSeeMoreContentListener, private val imageType:String): PagingDataAdapter<SeeMoreItems, RecyclerView.ViewHolder>(SeeMoreComparator) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(imageType){
            IMAGE_VERTICAL ->{
                var item = getItem(position)
                var verticalHolder = holder as SeeMoreVerticalVideoCustomHolder
                verticalHolder.bind(item!!)
                verticalHolder.mainLayout.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycleview_anim)

                verticalHolder.view.setOnClickListener {
                    if (item.type.equals("video")){
                        callBack.onSeeMoreContentClick("${item.id}" , VIDEO_CONTENT, premium = item.premium)
                    } else if (item.type.equals("series")){
                        callBack.onSeeMoreContentClick("${item.id}", SERIES_CONTENT, premium = item.premium)
                    } else {
                        callBack.onSeeMoreContentClick("${item.id}" , VIDEO_CONTENT, premium = item.premium)
                    }
                }
            }

            IMAGE_HORIZONTAL ->{
                var item = getItem(position = position)
                var horizontalHolder = holder as SeeMoreVideoCustomHolder
                horizontalHolder.bind(item!!)
                horizontalHolder.mainLayout.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycleview_anim)

                horizontalHolder.view.setOnClickListener {
                    if (item.type.equals("video")){
                        callBack.onSeeMoreContentClick("${item.id}" , VIDEO_CONTENT, premium = item.premium)
                    } else if (item.type.equals("series")){
                        callBack.onSeeMoreContentClick("${item.id}", SERIES_CONTENT, premium = item.premium)
                    } else {
                        callBack.onSeeMoreContentClick("${item.id}" , VIDEO_CONTENT, premium = item.premium)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(imageType){
            IMAGE_VERTICAL ->{
                SeeMoreVerticalVideoCustomHolder(ItemSeemoreVerticalBinding.inflate(LayoutInflater.from(parent.context), parent , false))
            }
            IMAGE_HORIZONTAL ->{
                SeeMoreVideoCustomHolder(ItemSeemoreVideoBinding.inflate(LayoutInflater.from(parent.context), parent , false))
            }
            else -> SeeMoreVideoCustomHolder(ItemSeemoreVideoBinding.inflate(LayoutInflater.from(parent.context), parent , false))
        }
    }


    object SeeMoreComparator: DiffUtil.ItemCallback<SeeMoreItems>() {
        override fun areItemsTheSame(oldItem: SeeMoreItems, newItem: SeeMoreItems): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SeeMoreItems, newItem: SeeMoreItems): Boolean {
            return oldItem == newItem
        }
    }


}

