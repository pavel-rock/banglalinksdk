package com.rockstreamer.iscreensdk.activity.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rockstreamer.iscreensdk.R
import com.rockstreamer.iscreensdk.adapter.CategoryMainAdapterWithoutAds
import com.rockstreamer.iscreensdk.api.viewmodel.SliderViewModel
import com.rockstreamer.iscreensdk.api.viewmodel.category.out.CategoryViewModelWithoutAds
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity(){

    abstract fun onCreateActivity()
    val sliderViewModel: SliderViewModel by inject()
    lateinit var categoryMainAdapterWithAds: CategoryMainAdapterWithoutAds
    val categoryViewModelWithAds: CategoryViewModelWithoutAds by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.iscreen_toolbar_color)
        onCreateActivity()
    }
}