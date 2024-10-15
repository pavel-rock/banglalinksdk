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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzM1NDUsInByb3ZpZGVyX2lkIjoidlBMckhqcXBpUmVrOUR6ek0zUmsySyIsInJvbGUiOjAsInVzZXJuYW1lIjoiKzg4MDE3OTgwOTk5NzYiLCJwbGF0Zm9ybSI6ImlzY3JlZW4iLCJzdWJzY3JpYmUiOmZhbHNlLCJwYWNrYWdlSW5mbyI6bnVsbCwiaXNUVk9EIjpmYWxzZSwidHZvZEV4cGlyZURhdGUiOiIxOTcwLTAxLTAxIiwiaWF0IjoxNzI4OTk1MDgwLCJleHAiOjE3MjkwODE0ODB9.FopauiuRQJXDOQfuitD18O6f64ILed0Zx5rADLdULM-mIWCZ_cNdoQB1YapJA9lFQf9JMbYO4p5WmYes_xUyUYQmf7VI20sycTJI3gG3ieJIMsWTvzYdidzPGSeEIjPIn7lwajFdYZP0YFvqdt5D9NCH99RoUUe-95RoMk6s3iRm8uI8MdHgywE5WQIBSZLTNwzhbh5NSXVsStFKVM24YkEVNp5_s3aUJZqVnZbHVRO_02AUiwCUr-hbYByo7zInRI7CJSKihGr70FyNOuxLa5BiDREX9xsDAd73v63dHkbwH_X5Rtwk8-eJMOVpD-_WQkx3L-ZxjzFbjuc__vMDTbC8OzA5KnpswZtg7tLojD6lpYFqLzW-GrzppIkLOqRQfQDBNFMZx8miz8p3E0q4o2mYMTjdVi6j-MsK-bORwl8dICpwsT2v4EuMVPTltaZW4bmvKoPUHrpOEVJ4wVA2ggWbbDvYiz3Vf1UXMWJz4vwSXNH7nnxK1S9rEp85_DocnFMO3cE19AcXQCSKdED_PDkhedNOXTVfipl1uPlWS1A877mAGY-f8qARlfNXJ467SloKFGx7uan1F0jOs02T5pUe6bZCavASbelD1A0Y_mgjbu9wNrxTGWMWOCEUpTXCS9QTayivPA_V7znMgXoAV9fl3u1mRLhNw1hNqwi4qOI")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}