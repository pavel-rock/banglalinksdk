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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzkxMzc5NCwicHJvdmlkZXJfaWQiOiI3MjM4NDcyOTM0NTI5ODQzNyIsInJvbGUiOjAsInVzZXJuYW1lIjoiKzg4MDE2NDczNzUyOTgiLCJwbGF0Zm9ybSI6ImlzY3JlZW4iLCJzdWJzY3JpYmUiOmZhbHNlLCJwYWNrYWdlSW5mbyI6bnVsbCwiaXNUVk9EIjpmYWxzZSwidHZvZEV4cGlyZURhdGUiOiIxOTcwLTAxLTAxIiwiaWF0IjoxNzI5NDk4NzkzLCJleHAiOjE3Mjk1ODUxOTN9.Wj97rjN067RIQbUHAtpwk2nYss0gZKBq17N8dh7tkOSGBF4QCi7yzbsR2D8W2TNoq8NW956IQjYMhnrVEawQRASikSNNQOhOCWyvVbGWGCFHxw_B6-i7AS7uXNFab5cCMu1ijSLKfUTqFdhSmoUUCUPQmWQY7MrctgKMkbd8dq-ePeVArrlH82VJ-G_qrwWO3GWCW9V-ThtQA_EaJyIGwBrsp8k-dLW0rPyrY55rWTnm9yIb7tx1fTTWYxdQjH-t1M6DpkIwqy7VfvSW-9SNniW7DH1OkQ1raCnvsoWuheRWcuoSluffIrzQHjFcGuLh4y4rDdA4gBrqJx0MskrO3RD2FhfSslY3Ajie3yh3CrKQPUYLJ-CF_9EGd4il0AfQ2l4JVhe4mGVjIXrm7n77ZCtIimvX_xOq8EzhYmdQdZSVUgHIQB0uZytGXQmfCi5u0Z7uZbaP8J3l25PLEe-k5iTG86ziPyfGiwJv9BWDQAMnLSyaZsVNoQmWwAMFBvEOBV1LMEJVkWSpH59PyFCZlkHpceRTOh3Fg7sissUAm2Zg-u9Bu0Ca8W1mlZvoeu3uOaHxM34OhMKgHBmFBX65A-MYaJVi1cnRbDlR979HN7fOK_QuJFrfOEiAfAQUyl9SvCtnbIFDk-oMZ0V0W1l3Ew0HyQ_2bwpvpYBZuly-PcY")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}