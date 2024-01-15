package com.rockstreamer.koinandroidexample

import android.app.Application
import android.content.Context
import com.rockstreamer.iscreensdk.di.contentModule
import com.rockstreamer.iscreensdk.di.networkModule
import com.rockstreamer.iscreensdk.di.viewModelModule
import com.rockstreamer.iscreensdk.utils.ISCREEN_SHAREDPREFERANCE
import com.rockstreamer.iscreensdk.utils.iScreenSDKInit
import com.rockstreamer.iscreensdk.utils.loginState
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        loginState = getSharedPreferences(ISCREEN_SHAREDPREFERANCE, Context.MODE_PRIVATE)
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NjYxNzgsInByb3ZpZGVyX2lkIjoiNzIzODQ3MjkzNDcyOTg0MzciLCJyb2xlIjowLCJ1c2VybmFtZSI6Iis4ODAxNzc4NDc0MzM0IiwicGxhdGZvcm0iOiJpc2NyZWVuIiwic3Vic2NyaWJlIjpmYWxzZSwicGFja2FnZUluZm8iOm51bGwsImlzVFZPRCI6ZmFsc2UsInR2b2RFeHBpcmVEYXRlIjoiMTk3MC0wMS0wMSIsImlhdCI6MTcwNTIzMTM2OSwiZXhwIjoxNzA1MzE3NzY5fQ.iIXVanY9vT9pq_AxNYJODhXL6IotDF0KClcaiUzOpOjdgavaWPu3AArPJ1JLmRCesTbrY3dWG6W6OZ2A0-1wloM2YZw1WnpZ-PTM16TPDavS60wx5xgb-ZHNlpPWw2K2tcjbU3mkIc8SSqH7CyAUSj6qzdIERxwhIH8f4s0Wp8RHnFmmu7iWkVBM6zPrc6KkvcKmKXM9YRi2V5P-v9lDedXyXyVTv7IvOWrnBet1Ej-0UeT0YvtHv3LPeRcSST6KVqbGfif-F3_S5O6nmIRvB1osc_suLGXqokr5-nKB2y3d4RM0xVaCk-colYQ188l_7xfzrGHUi-cpWeUdoHZJCxFYnSj-uEfCb4K1nxboNHI4uvNZ4SySj5ulLS6OODnTn7l0B34Crw2RHldZCQhhvNZZoLOb59pb0vjG1QJOLRhOOKPHQXleqZQ8eQvmalagu57rfirMSLUtPnQDsbhVKInoQdlMsa-bDnyUJYMGiX1gBQuVH4hEL3ww9j6lyerK8qZMm0tfVdlAiPXeTfZX0eqYi6JV3nfKKV5fCWc9HvXUU7OW1seNVs3a_i6D8TyRjr0atFsNF0zmYQ-GZOrDB41K0k7xAWYWmUJXmcY6GUqpRGL0LZqmUYm3NOnbMBf7znwnL5_sAzf1wUtGDKv8hNQKA9kv9RcYNNOCkS4xp40")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}