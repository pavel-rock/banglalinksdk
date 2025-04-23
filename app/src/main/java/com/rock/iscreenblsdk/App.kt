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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzM1NDUsInByb3ZpZGVyX2lkIjoidlBMckhqcXBpUmVrOUR6ek0zUmsySyIsInJvbGUiOjAsInVzZXJuYW1lIjoiKzg4MDE3OTgwOTk5NzYiLCJwbGF0Zm9ybSI6ImlzY3JlZW4iLCJzdWJzY3JpYmUiOmZhbHNlLCJwYWNrYWdlSW5mbyI6bnVsbCwiaXNUVk9EIjpmYWxzZSwidHZvZEV4cGlyZURhdGUiOiIxOTcwLTAxLTAxIiwiaWF0IjoxNzQ1Mzg3MDEzLCJleHAiOjE3NDU0NzM0MTN9.hKVzuQ37YM-kuEr83VI9zrn1E2MzBNLCDyJf7W00JEN9q2W56Yzy9f5eaK8d5D-iAWqEKcRrICYzPzngeFmwcrzJR6eFrgojbreF4r_JjV5uWEPziZIHU_h8TbDZ3w8lEqGKUsCsCLnZBJPNjEL-UcqAjOSQA55jfmHHZ8ekFkBQkNK_NQKCWV9je5wVZpOmCo-96Tlnxs2BVx-cVaO2NyrrX82QBaI-NTdu6lHsUoXugIGWqoZNFvbILBZ8iXA-5qqeDTygF6kOOeVkamtB3K4bJYY9PtfGYohJ5ZaQmzWOIhj3mKgBlR3jM5-fe3x9FYlAWKzbGSoYh_w6XhGvhf0923F3sraJmCcrV3JDENOCL2JieNk6VovgC7zXuRn4QqXAsApaqgK0PnjDSm7L_e_JsfZN_cd8ZLaJLHmHLwJKhWnTPiTiXg7BxoOPaOlaOjbySSGCntnrcRAKeNSQcSsntzQumKUWw57eQOCOq1RJ_IyyQWJ6DBmWrpbg4HSPU_R9vcrmuGg19zMzprd3zAtCj8s9OIk7Fc2S1a8--oaOaTifBYKl__ixeKIvp-YSwyLCQhZo2-kDW5feYJ71WTGzJwkFDnFy_Mqz39X9ao7aDrm-3pqMTVBHBR5KAKFAybidbNZ_qvLNRTLAJWfTZl71lWstOg4OZPPWOm9fiHQ")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}