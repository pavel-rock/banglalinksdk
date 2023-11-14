package com.rockstreamer.iscreensdk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rockstreamer.iscreensdk.adapter.custom.RecommandCustomViewHolder
import com.rockstreamer.iscreensdk.databinding.ItemVideoRecommandBinding
import com.rockstreamer.iscreensdk.listeners.OnRecommandCallback
import com.rockstreamer.iscreensdk.pojo.recommand.RecommendedResponse

class RecommandAdapter(private val onRecommandCallback: OnRecommandCallback):RecyclerView.Adapter<RecommandCustomViewHolder>(){

    var recommandList: ArrayList<RecommendedResponse> = arrayListOf()

    fun addAll(item : ArrayList<RecommendedResponse>){
        recommandList.clear()
        recommandList.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommandCustomViewHolder {
        return RecommandCustomViewHolder(ItemVideoRecommandBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecommandCustomViewHolder, position: Int) {
        var item = recommandList[position]
        holder.bind(item)
        holder.view.setOnClickListener {
            onRecommandCallback.onRecommandClick(item)
        }
    }

    override fun getItemCount() = recommandList.size
}