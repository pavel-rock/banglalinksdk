package com.rockstreamer.iscreensdk.listeners

import com.rockstreamer.iscreensdk.pojo.category.Contents


interface OnCategoryChildCallback {
    fun onCategoryItemCallback(contents: Contents)
}