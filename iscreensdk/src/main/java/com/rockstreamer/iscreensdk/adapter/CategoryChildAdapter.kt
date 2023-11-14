package com.rockstreamer.iscreensdk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rockstreamer.iscreensdk.adapter.custom.CategoryVerticalVideoCustomViewHolder
import com.rockstreamer.iscreensdk.adapter.custom.CategoryVideoCustomViewHolder
import com.rockstreamer.iscreensdk.databinding.ItemLayoutVideoBinding
import com.rockstreamer.iscreensdk.databinding.ItemVerticaleVideoBinding
import com.rockstreamer.iscreensdk.listeners.OnCategoryChildCallback
import com.rockstreamer.iscreensdk.pojo.category.Contents
import com.rockstreamer.iscreensdk.utils.IMAGE_HORIZONTAL
import com.rockstreamer.iscreensdk.utils.IMAGE_VERTICAL
import com.rockstreamer.iscreensdk.utils.SERIES_CONTENT
import com.rockstreamer.iscreensdk.utils.VIDEO_CONTENT
import com.rockstreamer.iscreensdk.utils.getContentType

class CategoryChildAdapter(var contentItems : ArrayList<Contents>, var type:String, var imageOrientation:String, var callback : OnCategoryChildCallback) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when {
            type.contentEquals("video") -> VIDEO_CONTENT
            type.contentEquals("series") -> SERIES_CONTENT
            else -> VIDEO_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIDEO_CONTENT -> {
                return if (imageOrientation== IMAGE_VERTICAL){
                    CategoryVerticalVideoCustomViewHolder(
                        ItemVerticaleVideoBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }else {
                    CategoryVideoCustomViewHolder(ItemLayoutVideoBinding.inflate(LayoutInflater.from(parent.context), parent , false))
                }
            }
            SERIES_CONTENT ->{
                return if (imageOrientation== IMAGE_VERTICAL){
                    CategoryVerticalVideoCustomViewHolder(
                        ItemVerticaleVideoBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }else {
                    CategoryVideoCustomViewHolder(ItemLayoutVideoBinding.inflate(LayoutInflater.from(parent.context), parent , false))
                }
            }
            else -> {
                return CategoryVideoCustomViewHolder(ItemLayoutVideoBinding.inflate(LayoutInflater.from(parent.context), parent , false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var contents = contentItems[position]
        when(getItemViewType(position)){
            VIDEO_CONTENT ->{
               if (imageOrientation == IMAGE_HORIZONTAL){
                   val videoHolder = holder as CategoryVideoCustomViewHolder
                   videoHolder.bind(contents)
                   videoHolder.view.setOnClickListener {
                       callback.onCategoryItemCallback(contents , getContentType(contents.type!!))
                   }
               }else{
                   val videoHolder = holder as CategoryVerticalVideoCustomViewHolder
                   videoHolder.bind(contents)
                   videoHolder.view.setOnClickListener {
                       callback.onCategoryItemCallback(contents , getContentType(contents.type!!))
                   }
               }
            }
            SERIES_CONTENT ->{
                if (imageOrientation == IMAGE_HORIZONTAL){
                    val videoHolder = holder as CategoryVideoCustomViewHolder
                    videoHolder.bind(contents)
                    videoHolder.view.setOnClickListener {
                        callback.onCategoryItemCallback(contents , getContentType(contents.type!!))
                    }
                }else{
                    val videoHolder = holder as CategoryVerticalVideoCustomViewHolder
                    videoHolder.bind(contents)
                    videoHolder.view.setOnClickListener {
                        callback.onCategoryItemCallback(contents , getContentType(contents.type!!))
                    }
                }
            }
        }
    }

    override fun getItemCount() = contentItems.size

}