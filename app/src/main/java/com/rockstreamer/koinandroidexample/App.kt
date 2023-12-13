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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDgzOTE3LCJwcm92aWRlcl9pZCI6IjcyMzg0NzI5MzQ3Mjk4NDM3Iiwicm9sZSI6MCwidXNlcm5hbWUiOiIrODgwMTc3ODQ3NDMzMCIsInBsYXRmb3JtIjoiaXNjcmVlbiIsInN1YnNjcmliZSI6ZmFsc2UsInBhY2thZ2VJbmZvIjpudWxsLCJpYXQiOjE3MDI0NjI5MzAsImV4cCI6MTcwMjU0OTMzMH0.LBbAX3PeYOoE9qmW3N4zKOsvj5H20BY0bs1aKPGURdbDZ0do_LdTcGaV9rhPE7ApDL4A1jTLx86T8TbCEkIHiQk2kjdxGG3zg1KLKpKxfcZOshPkQl6LUs4NAo2HxGMNiyK15mUyqvoZbs_639I842hqggn0ORqFwAuIHIe-RXgkjMkxlW19OAL1WDxwqZ2eBbAQYaSzATOR2SAaFOytDYH85Ip8SlEaHwiQOqKKkC-mUNXH2hyV-7dLGYXjUEQMRc6_aApmeJNinUhkdqTWmZzzJhBbW4xyyS6qIabKsns_7HqMNrfcrACVFXG0BGcyRWIPEbXCNcpdQXiov8NTnX52A6URDS2BOGCG5EVcht6_4Pc7H-cAmzPz6dEQiEpLFlZzwgLJFGW6Zh5bQ24ntFN5aXhghEqRSW-dvxnQBZ1KYNLzq0pxEuNwotf7cuWCf53WtPL9_Ca06MhBK4ncFcOZoq7QqXS_dpp1GEHpfL5oeJUZzzuuuhREh8dnDHWeeAajbeFMXXkzGNQbEVnktcAj9PmAoOWTYGEQC3HOzH3rf14GXtjEY7d5K9fLR55Ccpd4mZc7x04WA5SF9MgU4riES-kUdhjxyoKYZRT5LaXBaHTyNx9Gw2CsjI39N3gCmuGrwwj9rJSt-EP0q-n64R4v2h_FFQm1sgTcZTGgBDA")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}