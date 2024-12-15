package com.rock.iscreenblsdk

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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDQ0MzIyOCwicHJvdmlkZXJfaWQiOiI3MjM4NDcyOTg0NTIwOTIwMzciLCJyb2xlIjowLCJ1c2VybmFtZSI6Iis4ODAxNzk4MDk5OTg2IiwicGxhdGZvcm0iOiJpc2NyZWVuIiwic3Vic2NyaWJlIjpmYWxzZSwicGFja2FnZUluZm8iOm51bGwsImlzVFZPRCI6ZmFsc2UsInR2b2RFeHBpcmVEYXRlIjoiMTk3MC0wMS0wMSIsImlhdCI6MTczNDI1MDI4MCwiZXhwIjoxNzM0MzM2NjgwfQ.JXgJphPLXFJI3slGc0nLsvawjGPQC3VSGcpeyTgQGRfXAM4k-281VSuXUp29I3DB9l5QRcnIe9OLc43ULGcodkdXgtDt2NcS7LS-aP1oGEj0u7O2YGI7yVaH8mneCYLK27JK34NueV8SuS2u8lKVHZEjK4fi7HkLbMkHmSutFmbWww97D5zUCIS9u95iaVgJKVi40GtB4VH3KoYGXWGF7jOk0CSrVC8lzLlIZtgILc5PQfcViPcZdQ3vyYLXz-AIJdlX91hNZsJuWSVBw818OezamFWkaPEbeEZ5pz_ySQxVVmbigDRR5FyGobbgDf5L8PnqtNBGWw55nzihpGsJKcSrHCHS1sX2HFu9Vmsch7Wh8PfyPcGhsRPh537xK9aT1WHoD_wbhNABNjaeINp15-GhaxSghrVoy3JayGZXw4Z6o_p-eGuwHsaFFuqxRTpMHN_QqsZ5pDJY-h9sPOlmjbsZwfJhL2guPMABrzD1vUTCua_zzd_QiCeJDfJL8Jk9naVssS94SN1zUa_LZDDgu1zuIbfOmqL2Hu9nxgBmMbWJmCHx7ZUPz9QBv4aB2Q4KZ_RmtiUz5srEb2-wo6ShH4zkwCvQvsUNZ1r2-ECQCxAv08_OGefAtEzfPBo3_cGR5jmYbHng17FSJFxGV6YoXy9jobNavbgIxYh0FDw138c")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}