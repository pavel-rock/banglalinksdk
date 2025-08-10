package com.rockstreamer.iscreensdk.activity.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
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

        onCreateActivity()
    }
}