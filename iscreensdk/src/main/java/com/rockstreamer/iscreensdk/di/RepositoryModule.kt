package com.rockstreamer.iscreensdk.di


import com.rockstreamer.iscreensdk.api.repository.ContentRepository
import org.koin.dsl.module


val contentModule = module {
    single { ContentRepository(get()) }
}