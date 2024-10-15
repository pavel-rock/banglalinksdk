package com.rockstreamer.iscreensdk.listeners


import com.rockstreamer.iscreensdk.pojo.category.Contents
import com.rockstreamer.iscreensdk.pojo.category.FeatureContent

interface OnCategoryCallback {
    fun onCategorySeeMoreCallback(id:String, title:String , type:String, imageType:String)
    fun onCategoryChildClickCallback(contents: Contents)
    fun onFeatureContentClick(featureContent: FeatureContent)
}