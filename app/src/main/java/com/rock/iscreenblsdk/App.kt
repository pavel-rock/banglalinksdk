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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzkyNzYyMCwicHJvdmlkZXJfaWQiOiI3MjM4NDcyOTM0NTI5OTQzNyIsInJvbGUiOjAsInVzZXJuYW1lIjoiKzg4MDE2ODgxMjIzNTQiLCJwbGF0Zm9ybSI6ImlzY3JlZW4iLCJzdWJzY3JpYmUiOmZhbHNlLCJwYWNrYWdlSW5mbyI6bnVsbCwiaXNUVk9EIjpmYWxzZSwidHZvZEV4cGlyZURhdGUiOiIxOTcwLTAxLTAxIiwiaWF0IjoxNzI5NjY5NzUzLCJleHAiOjE3Mjk3NTYxNTN9.RJGretEP9U68apgTtDn_tJJX43WBWiTT79bHhR4dKkr51sOba8iwT91MB2iZzm8YNGgHePecuIvOuKyeAomk50a7BHYc5OhjnatbvgDy6Bko4S5TEZj8LPIQkrUZDEXAr_wxWqRTAZCrOV8O32BkOolP8MZ2ijNV_uMLOLJ5KROu12S1VXLutaB7BMsu1K0pTlFlMVJuYFUF3IfX3HaWja1pZqY3ZVx3VNrP598YQYn11OGuFccj4g0r-w3r_DYB9Yc96iFMkLU7wzU-UK6alP8mr7dhTGnHkU_yCJWpe9lE-J1IOccvjQZD8AYXxYwrtbRWJppxunWcJkeynRsPADIbF6QhStBuNvjPH_8Aq2giNh8KPKTe_RSPk02kwrdOwCzrmuPYq3C7ZyuI21dr5ZEBPJcxCww5sZwZsrjDfk0pHhU2c--_OwbnZlz_3qP5C3u2M9jkPozZHzllusCbwJ9jtJHs9HW87xTQUSzeYSVMGepNht0oVS9emL2vx4Lqfa5pEMXdv9C_fNZe1tJu0dJ31d24wDiVGO9HfmzE0LbL7R401tvGb_xfQGxBIrvP0kzs3-28MjwNaRC4yDdI_2rAfvnrrrWqVIszbp8n9nc4pZjhlLjo737Yf2LknrrXiFF7PjGPuvgp9baouDUpBwumJ7FjKzEefs14NH6TJsg")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}