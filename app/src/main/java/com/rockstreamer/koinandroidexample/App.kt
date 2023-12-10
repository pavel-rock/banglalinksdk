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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDE0OSwicHJvdmlkZXJfaWQiOiJNbjdaNnpXN2QyWkpLZktlSGJnOFZFNnNSMXoyIiwicm9sZSI6MCwidXNlcm5hbWUiOiIrODgwMTc3ODQ3NDMzMyIsInBsYXRmb3JtIjoiaXNjcmVlbiIsInN1YnNjcmliZSI6ZmFsc2UsInBhY2thZ2VJbmZvIjpudWxsLCJpYXQiOjE3MDIyMTEzODgsImV4cCI6MTcwMjI5Nzc4OH0.M3Rr7bv1IrX7SuSw3BTlV2_rCliDHQtiXQdeHZRIdBxBDBt3Se-UgjFRyKtXB9QtQMRq2AA-ytfqnrRfHeGtlCEY9wnCaNm8bErJrzF2lLgQvSJCUA2xfiI1bOGsAnB3RJn2hHukLLF2VkJakz2CAyu_LfpZKtcKVmAyaXFGYPpr9oW1Kl4hnRtFrTF-3rjMD9tx_W9z5iCRANbA-41hz3w2eunmphbmccIkzib3l7PJS7Drd5-4IoF4Zxo08v90OGdXVXSvB6J3a6L6hhvhK2jz-K537Czagj9QEVy9azvIiQ4RrzRma90vIZgX_ZsHOgoclHmbIas90URcNIjKwTYla_-MnbGFCSS7NaBKbgBEz7KdTDn_Fwl9aLVrNqZvHhmyAo5fmdRzWOmNDaVDYmZDXamCrxnui59PbuQTTSB9SiZqXF3KBL3Lok1u0RmYsKD3PNDgpOW0svfdgLX0mMNzrbW31gVUUGOOcf5tUuFACT8W4gyjWZm5SLj-gDteYkCtlhJHVjP9fKhgvP16MNBj8SxptclHTcma0lBORLIJu900HRdoanZjZ2cssY7HWEjimSJRkQ5uauAqYjjySuPa6tIVo4rDEaFEqI_N_4iDwWFZHl6IbMdPTORy75N-grAr74MM8VOYoP4n8HeX1tjD3RRUBY3jjqG7arheHwQ")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}