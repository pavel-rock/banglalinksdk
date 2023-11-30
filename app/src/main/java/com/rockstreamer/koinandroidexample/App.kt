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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IasdjalsdjalskdjaldskjkpXVCJ9.eyJpZCI6Mjk2OTEwLCJwcm92aWRlcl9pZCI6ImRHdTd6Q1NIMVRFTHZRMVhKY2JXaU4iLCJyb2xlIjowLCJ1c2VybmFtZSI6Iis4ODAxODg0MDQzNjAzIiwicGxhdGZvcm0iOiJpc2NyZWVuIiwic3Vic2NyaWJlIjpmYWxzZSwicGFja2FnZUluZm8iOm51bGwsImlhdCI6MTcwMTI5MTEzOSwiZXhwIjoxNzAxMzc3NTM5fQ.OjMySAzG4Zgq7zL4QxMN40V8cEVLhYafPqN1UkmtjFQykNcb9fZrZFaTPW0bdcrEhzfmA-mQ5-UlZ-H5PA3ggbCpKnEOGd_kA_8Ov_wcC2V07K9gzho2dtbywDWaR9grWCUM0_VrnsgF0eI9_PV3mdUXQV9GSLMn7zgK2-qhdKR0i8oBFEAXK2jjF5E5HvYmMYjR3lGMcvasrGhoa-6OEa9HSf9G1miXAngaVlK9k9-hpkiWOe49u_2D3uP_utSGlz4gi6AiArGe8Q7p8HHidWMtcLFJpSd_l02Pr3CpcQzaWgaFjxxOnhD6P1tUS02TIWBWWUnTjVMOAOI0HdwjW-053hpctyzq4eAFhMnMIyqkxrXt6cZ1X7N1xSgTcn_FUCOjoj8pDIleP1wjTjj0YmHcVx43wFtvwHU1kGqbJ9jDzGXncvlIWLweOfy_3Yu61FMi3V4v738t8vtC6FVacoi1ixNAMwGBnZdtxkCB35tJ-NcyZHDIZhLPPi28J6JS6zx7pxWtlwMDLSc32MsLZGuPZ2Tq4kjOMP8GZayp7OS18QCdZhVC25Mr0gxrYTzestfIpEUtCccWRJaKKzBFW8QcVkGofRrCkzSVcEzhr4U3f11VeG5cziRs1KnXVKHeFE0bdQtwrY90sAaWSHIu6S8rzPrIFCKcheqU9VzoTtw")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}