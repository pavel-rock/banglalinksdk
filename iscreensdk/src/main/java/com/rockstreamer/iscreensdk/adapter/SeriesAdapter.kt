package com.rockstreamer.iscreensdk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rockstreamer.iscreensdk.adapter.custom.SeriesCustomHolder
import com.rockstreamer.iscreensdk.databinding.ItemSeriesBinding
import com.rockstreamer.iscreensdk.listeners.OnSeriesCallBack
import com.rockstreamer.iscreensdk.pojo.series.EpisodeItem

class SeriesAdapter(var onSeriesCallBack: OnSeriesCallBack) : RecyclerView.Adapter<SeriesCustomHolder>(){

    var seriesList : MutableList<EpisodeItem> = arrayListOf()

    fun add(EpisodeItem: EpisodeItem){
        seriesList.add(EpisodeItem)
    }

    fun clearList(){
        seriesList.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: ArrayList<EpisodeItem>){
        seriesList = arrayListOf()
        seriesList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = seriesList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesCustomHolder {
        return SeriesCustomHolder(ItemSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SeriesCustomHolder, position: Int) {
        var item = seriesList[position]
        holder.bind(item)
        holder.view.setOnClickListener {
            onSeriesCallBack.onSeriesCallback(item, holder.absoluteAdapterPosition)
        }
    }
}