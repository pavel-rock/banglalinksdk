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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDE0NTAsInByb3ZpZGVyX2lkIjoiNzIzODQ3MjIzNDcyOTg0MzciLCJyb2xlIjowLCJ1c2VybmFtZSI6Iis4ODAxODE2NDg5MDI3IiwicGxhdGZvcm0iOiJpc2NyZWVuIiwic3Vic2NyaWJlIjp0cnVlLCJwYWNrYWdlSW5mbyI6eyJuYW1lIjoiTW9udGhseVN1YnMgKCBFeHBpcmUgb24gMjAyNC0xMS0xNCApIiwiZGVzY3JpcHRpb24iOiJNb250aGx5U3VicyAoIEV4cGlyZSBvbiAyMDI0LTExLTE0ICkiLCJwcmljZSI6IjM1IiwidW5pdCI6IkJEVCIsImNvdW50cnlDb2RlIjoiQkQiLCJjb2xvckNvZGUiOm51bGwsInByb2R1Y3RJZCI6IklTQ1JFRU5fU1NMX1BMQU5fMSIsInBheW1lbnRNZXRob2RUeXBlIjoic3NsY29tbWVyeiIsImV4cGlyZURhdGUiOiIyMDI0LTExLTE0In0sImlzVFZPRCI6ZmFsc2UsInR2b2RFeHBpcmVEYXRlIjoiMTk3MC0wMS0wMSIsImlhdCI6MTcyOTAyNzA2MSwiZXhwIjoxNzI5MTEzNDYxfQ.aEVAcr-b6whE0XW5-8b0KTqi4DH6bGsBmTVVjBaLf6GpL9I6NOwEbEU8AQ9WLoOAeIdCOZtJEC_oPtA9V-NdKKyMNLnzEad8Oer8ppk4KEwGEfEzpmX1bBjQiVOMwS3wfol2VHo2l1Hp3Z6areLMDRzYPdATgwhIvB7y2-aHupgGj5LWe4OgIiaaDFgQyKP18ogOT61UVAssxmjU-raWBOyxxrr8g_zXakUuzTxZCcPYRRbt2ZNWfjRjmXivjz-eUjjwXhFDToxifdhbFk0yIqoLb-28vD3HB07tL-1rXcQEvGBKCJiUoGgx2ruClJMD8kqBS7soK-9NkrgiYDyfL7bP3MKlvZCngB1Tj7-VAvvtRwyklJR8oK7Fk_ZANzPXNVSAO0zqobQHjHaJdILl3Lkq2-3BN262SHsswfRO3N_5ENhugAG-tSOTT6GC5s9H01xNBaiXmacrSn6lhy2I9Au5d0lPaMGekDfUCBg9NHNhMjODrVz8TX7H6F6OsAE7mU9ddVT9WzZeF2446WriMkAic7Ph9-VRTWEDiyGgD35DiwKt3gx5iWWWj_X4xejMZp9_nsPwiKrsHzC0Hyrtt-JrE-Ag9u08aWRSMq7wViJPwOhhbjJLygVlcSj1f0fDR-B3X5frJskkvDfIeErEdPLyJkP7KdLlFwaSdKvn_eQ")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}