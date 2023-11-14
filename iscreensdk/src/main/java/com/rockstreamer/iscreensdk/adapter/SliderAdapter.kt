package com.rockstreamer.iscreensdk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.islamkhsh.CardSliderAdapter
import com.rockstreamer.iscreensdk.R
import com.rockstreamer.iscreensdk.listeners.onBannerCallback
import com.rockstreamer.iscreensdk.pojo.slider.SliderResponse
import com.rockstreamer.iscreensdk.utils.BASE_BANNER_IMAGE
import com.rockstreamer.iscreensdk.utils.RoundRectCornerImageView

class SliderAdapter(var context: Context, var items: ArrayList<SliderResponse>, var onBanner: onBannerCallback) : CardSliderAdapter<SliderAdapter.MovieViewHolder>() {

    override fun getItemCount(): Int = items.size

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var view = view
        var banner = view.findViewById(R.id.movie_poster) as RoundRectCornerImageView
    }

    override fun bindVH(holder: MovieViewHolder, position: Int) {
        var item = items[position]
        Glide.with(context).load(BASE_BANNER_IMAGE + item.path).placeholder(R.drawable.ic_video_thumb).into(holder.banner)
        holder.view.setOnClickListener {
            onBanner.onBannerClick(item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_banner, parent, false)
        return MovieViewHolder(view)
    }
}