package com.rockstreamer.iscreensdk.di

import com.rockstreamer.iscreensdk.api.viewmodel.seemore.SeeMoreViewModel
import com.rockstreamer.iscreensdk.api.viewmodel.SliderViewModel
import com.rockstreamer.iscreensdk.api.viewmodel.category.out.CategoryViewModelWithoutAds
import com.rockstreamer.iscreensdk.api.viewmodel.details.VideoDetailsViewModel
import com.rockstreamer.iscreensdk.api.viewmodel.recommand.RecommandViewModel
import com.rockstreamer.iscreensdk.api.viewmodel.series.SeriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { CategoryViewModelWithoutAds(get()) }
    viewModel { SliderViewModel(get(), get()) }
    viewModel { SeeMoreViewModel(get()) }
    viewModel { SeriesViewModel(get(), get()) }
    viewModel { VideoDetailsViewModel(get(), get()) }
    viewModel { RecommandViewModel(get(), get()) }
}