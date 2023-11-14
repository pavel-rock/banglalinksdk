package com.rockstreamer.iscreensdk.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rockstreamer.iscreensdk.R

class ImageLoader {

    companion object{
        fun loadVideoImage(context: Context, url:String, imageView: ImageView){
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_video_thumb).into(imageView)
        }
        fun loadMovieImage(context: Context, url:String, imageView: ImageView){
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_movie_placeholder).into(imageView)
        }
    }
}