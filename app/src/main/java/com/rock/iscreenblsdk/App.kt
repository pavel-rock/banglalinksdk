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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzM1NDUsInByb3ZpZGVyX2lkIjoidlBMckhqcXBpUmVrOUR6ek0zUmsySyIsInJvbGUiOjAsInVzZXJuYW1lIjoiKzg4MDE3OTgwOTk5NzYiLCJwbGF0Zm9ybSI6ImlzY3JlZW4iLCJzdWJzY3JpYmUiOmZhbHNlLCJwYWNrYWdlSW5mbyI6bnVsbCwiaXNUVk9EIjpmYWxzZSwidHZvZEV4cGlyZURhdGUiOiIxOTcwLTAxLTAxIiwiaWF0IjoxNzI5MDAxNDQ5LCJleHAiOjE3MjkwODc4NDl9.a8_YFCX6iBaUdHpX_SAu1u-8v9OTg8yzSSMreP45aDeXlHIGUj_Wl9Qd2TYxT8Lq4fRNSuOpJ35igVBZaPoGskCK0B4nnhRQHc4f__jn0nvmI6kGA7CbnG0b9Qwq7WDlXk0CbiOSZrWy2C4KvT2K-hxrws9bLGOzrwFwfc7-KXklnpaPToYqKQE4XoU_JGDDFo3fZJucLRzaC6NvcVnupVk7S9ikuUnFBAdgDWtROyMf435tIB0APh45ntD_VuL4LrEY3klavgEBNDEiceKCgc-IU9OET3XJj2RyIxlhvZwMBs2Ann_RQdjydAZtMV89E9c6NBY8POea37w5WDVNjknB8DIY2HIeyLyaF484gjdpzccsyao1RBalDLN8gNo-ETIUPY3-K7shlMx1qBTDw01R2FTbikgwolmvs49IlfzhgV1HxvccRmbkCUssxZNEPq0oeUC11w6lURfG4MsM_hug3Y0gcTlClVgmK_Lk9f_Czc_5qGpjT7ahZKAc0YON409MW3jV4ce8lZv2xuY1zzV5X8hq0BU8mFourvRoi2I-37vQVA_MEGBiMr2C7U701fZd-erNUti05hyMABL8gTlWjtIq4s8e80SmrH9JmsMlM1TZnRsryICun_B6I4ioT67glzWEe5H0h5bMBc7XKTuAS39bD6tpVNOzm1mqo2w")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}