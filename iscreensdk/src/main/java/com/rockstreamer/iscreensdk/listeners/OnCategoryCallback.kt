package com.rockstreamer.iscreensdk.listeners


import com.rockstreamer.iscreensdk.pojo.category.Contents
import com.rockstreamer.iscreensdk.pojo.category.FeatureContent

interface OnCategoryCallback {
    fun onCategorySeeMoreCallback(id:String, title:String , type:Int, imageType:String)
    fun onCategoryChildClickCallback(contents: Contents, type: Int)
    fun onFeatureContentClick(featureContent: FeatureContent, type: Int)
}