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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDE0OSwicHJvdmlkZXJfaWQiOiJNbjdaNnpXN2QyWkpLZktlSGJnOFZFNnNSMXoyIiwicm9sZSI6MCwidXNlcm5hbWUiOiIrODgwMTc3ODQ3NDMzMyIsInBsYXRmb3JtIjoiaXNjcmVlbiIsInN1YnNjcmliZSI6ZmFsc2UsInBhY2thZ2VJbmZvIjpudWxsLCJpc1RWT0QiOmZhbHNlLCJ0dm9kRXhwaXJlRGF0ZSI6IjE5NzAtMDEtMDEiLCJpYXQiOjE3MDQzNTY0MzUsImV4cCI6MTcwNDQ0MjgzNX0.XvyYHJarG5oFIZtDLNUc7YtrDX7Jfv7YlHtNzOa4dNDPF1cMxP0KqioTtILb716CFdfCIsfryJJwwOZOCBMKzq6aLpMIrGwPQEzkXCQExy6J8a2vT7to016E7DNC-tk4d6l8jt3Gb5tn3bvau9dmm520VShG3RSGhgM8i-aOBKbJeFeLNQ1Jn_O_U_U1zSYdp7nAL5o2MfuKGQ8tjTiLDQod-0x1e9q5BZay1bFlZETtoq802vNZFIYV0_91JyOTYiQBp4bL0v2GuIuuDfaGJPM3aAkjYevJ-vnyULDjxKwXzJk9ncKgNGPx8nL3pkYjQMaXMqfvOMw0Pk14-VZ9tzNkfF8KiUxLdkOI2cLQOY10_QmeTpTXCWlv1KkRqM6qYsUnX15veAWCmly0Ip55xP_H20G2Sj1u5bndCbaouWEicbS1JhS_nzjP331c8YMHbecuXu66zjbNOsxCXMdNUMAEvD5dsiAy5qxULsUH8vWzuwjU2dERXx5Sg2aNUfbliwbMZKcvzkFMysuj9m2SXUrdwVnZUjUJhcj77ztHHKSM8gnTs3_J0l9jCMcArY_SgW9ENwoHDX9qFVq0RLfj481zUV9qPqBlwDNq_V8OPN5lDzdJZEB00OZb-dwDlpjvlWV-UEMB4Dwj7bg6vCaeR_1sTemI2Lyjy2PB80BpuX8")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}