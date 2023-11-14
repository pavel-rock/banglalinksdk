package com.rockstreamer.iscreensdk.adapter

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.rockstreamer.iscreensdk.R
import com.rockstreamer.iscreensdk.databinding.ItemCategoryRowBinding
import com.rockstreamer.iscreensdk.databinding.ItemCategoryVideoVerticalBinding
import com.rockstreamer.iscreensdk.listeners.OnCategoryCallback
import com.rockstreamer.iscreensdk.listeners.OnCategoryChildCallback
import com.rockstreamer.iscreensdk.pojo.category.CategoryItems
import com.rockstreamer.iscreensdk.pojo.category.Contents
import com.rockstreamer.iscreensdk.pojo.category.FeatureContent
import com.rockstreamer.iscreensdk.utils.EqualSpacingItemDecoration
import com.rockstreamer.iscreensdk.utils.IMAGE_URL
import com.rockstreamer.iscreensdk.utils.ITEM_SPACE
import com.rockstreamer.iscreensdk.utils.SERIES_CONTENT
import com.rockstreamer.iscreensdk.utils.StartSnapHelper
import com.rockstreamer.iscreensdk.utils.VIDEO_CONTENT
import com.rockstreamer.iscreensdk.utils.VIDEO_CONTENT_HORIZONTAL
import com.rockstreamer.iscreensdk.utils.VIDEO_CONTENT_VERTICAL
import com.rockstreamer.iscreensdk.utils.bindFeaturebutton
import com.rockstreamer.iscreensdk.utils.getMostPopulousSwatch
import com.rockstreamer.iscreensdk.utils.gone
import com.rockstreamer.iscreensdk.utils.show
import com.rockstreamer.iscreensdk.utils.showGenre

class CategoryMainAdapterWithoutAds(var callback: OnCategoryCallback): PagingDataAdapter<CategoryItems, RecyclerView.ViewHolder>(
    CategoryComparator
), OnCategoryChildCallback {

    private var categoryChildAdapter: CategoryChildAdapter?=null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when(getItemViewType(position)){
            VIDEO_CONTENT_VERTICAL ->{
                val videoHolder = holder as CustomCategoryVerticalVideoViewHolder
                verticalViewHolder(videoHolder , position = position)
            }

            VIDEO_CONTENT_HORIZONTAL ->{
                val videoHolder = holder as CustomCategoryViewHolder
                horizontalViewHolder(videoHolder , position = position)
            }
            SERIES_CONTENT ->{
                val videoHolder = holder as CustomCategoryVerticalVideoViewHolder
                verticalViewHolder(videoHolder , position = position)
            }
        }
    }

    private fun verticalViewHolder(holder : CustomCategoryVerticalVideoViewHolder, position: Int){
        val categoryItem = getItem(position) as CategoryItems
        holder.bindContent(categoryItem)

        if (categoryItem.feature_contents!=null){
            holder.bindFeatureShowHide(true)
            bindFeaturebutton(categoryItem.type!!, holder.featureButtonClick)
            holder.bindFeature(categoryItem.feature_contents[0])

            if (categoryItem.feature_contents[0].premium){
                holder.featurePremium.imagePremium.show()
            }else{
                holder.featurePremium.imagePremium.gone()
            }
        } else holder.bindFeatureShowHide(false)

        holder.mainLayout.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycleview_anim)

        if (categoryItem.type!=null && categoryItem.contents!=null){
            categoryChildAdapter = categoryItem.imageOrientation?.let { CategoryChildAdapter(categoryItem.contents, categoryItem.type!!, imageOrientation = it, this) }
            holder.setChildRecycleView(categoryChildAdapter!!)
        }

        holder.featureLayout.setOnClickListener {
            bindFeatureCallback(categoryItem.feature_contents[0])
        }

        holder.featureButtonClick.setOnClickListener {
            bindFeatureCallback(categoryItem.feature_contents[0])
        }

        holder.seeMoreButton.setOnClickListener {
            bindSeeMore(categoryItem)
        }

        holder.seeMoreText.setOnClickListener {
            bindSeeMore(categoryItem)
        }

    }

    private fun horizontalViewHolder(holder : CustomCategoryViewHolder, position: Int){
        val categoryItem = getItem(position) as CategoryItems
        holder.bindContent(categoryItem)

        if (categoryItem.feature_contents!=null){
            holder.bindFeatureShowHide(true)
            holder.bindFeature(categoryItem.feature_contents[0])
            bindFeaturebutton(categoryItem.type!!, holder.featureButtonClick)
            if (categoryItem.feature_contents[0].premium){
                holder.featurePremium.imagePremium.show()
            }else{
                holder.featurePremium.imagePremium.gone()
            }
        } else holder.bindFeatureShowHide(false)

        holder.mainLayout.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycleview_anim)

        if (categoryItem.type!=null && categoryItem.contents!=null){
            categoryChildAdapter = categoryItem.imageOrientation?.let { CategoryChildAdapter(categoryItem.contents, categoryItem.type!!, imageOrientation = it, this) }
            holder.setChildRecycleView(categoryChildAdapter!!)
        }

        holder.featureLayout.setOnClickListener {
            bindFeatureCallback(categoryItem.feature_contents[0])
        }

        holder.featureButtonClick.setOnClickListener {
            bindFeatureCallback(categoryItem.feature_contents[0])
        }

        holder.seeMoreButton.setOnClickListener {
            bindSeeMore(categoryItem)
        }

        holder.seeMoreText.setOnClickListener {
            bindSeeMore(categoryItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIDEO_CONTENT_VERTICAL ->{
                CustomCategoryVerticalVideoViewHolder(ItemCategoryVideoVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }

            VIDEO_CONTENT_HORIZONTAL ->{
                CustomCategoryViewHolder(ItemCategoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }

            SERIES_CONTENT ->{
                CustomCategoryVerticalVideoViewHolder(ItemCategoryVideoVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> {
                CustomCategoryViewHolder(ItemCategoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position= position)?.type == "video" && getItem(position = position)?.imageOrientation == "horizontal"){
            VIDEO_CONTENT_HORIZONTAL
        }else if(getItem(position = position)?.type == "video" && getItem(position = position)?.imageOrientation=="vertical"){
            VIDEO_CONTENT_VERTICAL
        }else if (getItem(position = position)?.type == "series"){
            SERIES_CONTENT
        }else {
            VIDEO_CONTENT_HORIZONTAL
        }
    }


    private fun bindFeatureCallback( featureContent: FeatureContent){
        when(featureContent.type){
            "video" -> callback.onFeatureContentClick(featureContent = featureContent,  VIDEO_CONTENT)
            "series" -> callback.onFeatureContentClick(featureContent = featureContent,  SERIES_CONTENT)
        }
    }

    private fun bindSeeMore(categoryItems: CategoryItems){
        when(categoryItems.type){
            "video" -> callback.onCategorySeeMoreCallback("${categoryItems.id}","${categoryItems.title}", VIDEO_CONTENT, imageType = categoryItems.imageOrientation!!)
            "series" -> callback.onCategorySeeMoreCallback("${categoryItems.id}","${categoryItems.title}", SERIES_CONTENT, imageType = categoryItems.imageOrientation!!)
        }
    }

    inner class CustomCategoryViewHolder(private val binding: ItemCategoryRowBinding):RecyclerView.ViewHolder(binding.root){
        var featureLayout = binding.featureMainLayout.featureLayout
        var featureButtonClick = binding.featureMainLayout.buttonFeatureWatch
        var seeMoreButton = binding.moreImage
        var seeMoreText = binding.textMore
        var mainLayout = binding.mainLayout
        var featurePremium = binding.featureMainLayout.premiumLayout

        init {
            binding.homeRecyclerViewHorizontal.setHasFixedSize(true)
            binding.homeRecyclerViewHorizontal.isNestedScrollingEnabled = false
            binding.homeRecyclerViewHorizontal.layoutManager = LinearLayoutManager(binding.featureMainLayout.featureTitle.context, LinearLayoutManager.HORIZONTAL , false)
            binding.textCategoryMore.text = binding.featureMainLayout.featureTitle.context.resources.getString(R.string.more)

            binding.homeRecyclerViewHorizontal.addItemDecoration(
                EqualSpacingItemDecoration(
                    ITEM_SPACE,
                    EqualSpacingItemDecoration.HORIZONTAL
                )
            )
            //binding.homeRecyclerViewHorizontal.itemAnimator = DefaultItemAnimator()
        }

        fun bindContent(categoryItems: CategoryItems){
            binding.textCategoryMore.text = categoryItems.title
        }

        fun bindFeature(featureContent: FeatureContent){
            //binding.featureMainLayout.featureItem = featureContent
            //binding.featureMainLayout.featureGenre = showGenre(featureContent.genres)

            Glide.with(binding.featureMainLayout.featureBackground.context)
                .asBitmap()
                .load(IMAGE_URL +featureContent.horizontalThumbnails[0].path)
                .placeholder(R.drawable.ic_video_thumb)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val palette: Palette = Palette.from(resource).generate()
                        val vibrant = getMostPopulousSwatch(palette)

                        val colors = intArrayOf( vibrant!!.rgb , Color.parseColor("#2B2D36"))
                        val gd = GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM, colors
                        )
                        gd.cornerRadius = 0f
                        binding.featureMainLayout.featureBackground.background = gd
                        binding.featureMainLayout.imageFeature.setImageBitmap(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })


//            if (featureContent.description!!.length >80){
//                var testString:String = "${featureContent.description}"
//                var upto :String = testString.substring(0 , testString.length.coerceAtMost(80))
//                "${upto}....".also { binding.featureMainLayout.featureDescription = it }
//            } else {
//                if (featureContent.description!=null){
//                    binding.featureMainLayout.featureDescription = featureContent.description
//                }
//            }
        }

        fun bindFeatureShowHide(value:Boolean){
            if (value){
                binding.featureMainLayout.featureLayout.show()
            } else {
                binding.featureMainLayout.featureLayout.gone()
            }
        }

        fun setChildRecycleView(adapter: CategoryChildAdapter){
            val snapHelperStart = StartSnapHelper()
            snapHelperStart.attachToRecyclerView(binding.homeRecyclerViewHorizontal)
            var recycledViewPool = RecyclerView.RecycledViewPool()
            binding.homeRecyclerViewHorizontal.setRecycledViewPool(recycledViewPool)
            binding.homeRecyclerViewHorizontal.adapter = adapter
        }
    }

    inner class CustomCategoryVerticalVideoViewHolder(private val binding: ItemCategoryVideoVerticalBinding):RecyclerView.ViewHolder(binding.root){
        var featureLayout = binding.featureMainLayout.featureLayout
        var featureButtonClick = binding.featureMainLayout.buttonFeatureWatch
        var seeMoreButton = binding.moreImage
        var seeMoreText = binding.textMore
        var mainLayout = binding.mainLayout
        var featurePremium = binding.featureMainLayout.premiumLayout


        init {
            binding.homeRecyclerViewHorizontal.setHasFixedSize(true)
            binding.homeRecyclerViewHorizontal.isNestedScrollingEnabled = false
            binding.homeRecyclerViewHorizontal.layoutManager = LinearLayoutManager(binding.featureMainLayout.featureTitle.context, LinearLayoutManager.HORIZONTAL , false)
            binding.textCategoryMore.text = binding.featureMainLayout.featureTitle.context.resources.getString(R.string.more)

            binding.homeRecyclerViewHorizontal.addItemDecoration(
                EqualSpacingItemDecoration(
                    ITEM_SPACE,
                    EqualSpacingItemDecoration.HORIZONTAL
                )
            )
            //binding.homeRecyclerViewHorizontal.itemAnimator = DefaultItemAnimator()
        }

        fun bindContent(categoryItems: CategoryItems){
            binding.textCategoryMore.text = categoryItems.title
        }

        fun bindFeature(featureContent: FeatureContent){
            //binding.featureMainLayout.featureItem = featureContent
            //binding.featureMainLayout.featureGenre = showGenre(featureContent.genres)

            Glide.with(binding.featureMainLayout.featureBackground.context)
                .asBitmap()
                .load(IMAGE_URL +featureContent.horizontalThumbnails[0].path)
                .placeholder(R.drawable.ic_video_thumb)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val palette: Palette = Palette.from(resource).generate()
                        val vibrant = getMostPopulousSwatch(palette)

                        val colors = intArrayOf( vibrant!!.rgb , Color.parseColor("#2B2D36"))
                        val gd = GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM, colors
                        )
                        gd.cornerRadius = 0f
                        binding.featureMainLayout.featureBackground.background = gd
                        binding.featureMainLayout.imageFeature.setImageBitmap(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })

//            if (featureContent.description!!.length >80){
//                var testString:String = "${featureContent.description}"
//                var upto :String = testString.substring(0 , testString.length.coerceAtMost(80))
//                "${upto}....".also { binding.featureMainLayout.featureDescription = it }
//            } else {
//                if (featureContent.description!=null){
//                    binding.featureMainLayout.featureDescription = featureContent.description
//                }
//            }
        }

        fun bindFeatureShowHide(value:Boolean){
            if (value){
                binding.featureMainLayout.featureLayout.show()
            } else {
                binding.featureMainLayout.featureLayout.gone()
            }
        }

        fun setChildRecycleView(adapter: CategoryChildAdapter){
            val snapHelperStart = StartSnapHelper()
            snapHelperStart.attachToRecyclerView(binding.homeRecyclerViewHorizontal)
            var recycledViewPool = RecyclerView.RecycledViewPool()
            binding.homeRecyclerViewHorizontal.setRecycledViewPool(recycledViewPool)
            binding.homeRecyclerViewHorizontal.adapter = adapter
        }
    }


    object CategoryComparator: DiffUtil.ItemCallback<CategoryItems>() {
        override fun areItemsTheSame(oldItem: CategoryItems, newItem: CategoryItems): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryItems, newItem: CategoryItems): Boolean {
            return oldItem == newItem
        }
    }



    override fun onCategoryItemCallback(contents: Contents, type: Int) {
        callback.onCategoryChildClickCallback(contents, type)
    }
}
