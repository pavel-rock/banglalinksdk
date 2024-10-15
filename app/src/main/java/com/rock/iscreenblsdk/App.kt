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
        iScreenSDKInit(apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NDE0NTEsInByb3ZpZGVyX2lkIjoiNzIzODQ3MjIzNDcyOTg0MzciLCJyb2xlIjowLCJ1c2VybmFtZSI6Iis4ODAxODE2NDg5MDI5IiwicGxhdGZvcm0iOiJpc2NyZWVuIiwic3Vic2NyaWJlIjpmYWxzZSwicGFja2FnZUluZm8iOm51bGwsImlzVFZPRCI6ZmFsc2UsInR2b2RFeHBpcmVEYXRlIjoiMTk3MC0wMS0wMSIsImlhdCI6MTcyOTAyNDQ5OSwiZXhwIjoxNzI5MTEwODk5fQ.CfA4RSO7WYCQt7_FcDt3SM-2z3ddvrucwCyCGbm3wS3nx2cVjb-KzzIdadDDFRo15afiUaxGn1siwvtpCcvODjI1-JbZIT1kQ0SQ-mMCLPHFmpTRmdE8kHjsxGv6BwqMAfMD9K51zZQTWUTnkaFVfRcyLNku43iMvVTWbYdt517kdVYg3AzTRALbxL1LtbaQGjy7p5WWBJx4ri8CA4qn5aHYPBijk90tq9XJyleN9ETio23A-NJD7LFG3J240tlmRqKsjFx40sX8wiWSXr-d7Pm_735_eUWGQdLEPCHRxnCPmlmXqpqY3XF7GMV4fBHdN5fsH1pmzTIKxL_8NOkQ3e3coJyK3LkycL9UJ8Bqym__GnUxpdiwSwfRT41T0nzgnh8uHUtuVqDl-i2FDWewNLuf1xSCjlYmCRwXahnkT1w5l_IC17EJycU5D9MwXGkJdAtYltlYxFZ-JT3nPzs7xnjdqvQcsoYDyRujDWbvul6OGnbyM7uq848yev0SFzV60MPzy_9BNqG11yJxNaCXDW0Ow08YefIY0ctWiq3VakBrxc_SEY3sooXc1jb86kpltwqkQJPBcWiOEsit-PqL8xNBx_3bweS_ByC5_zIZU5my3-HGbOKmJt3tzm-JT9RRYp6KNeo0JIGYU1siAMqK8y2xrjDMlrsIVTGlHV44c8w")
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule , contentModule , viewModelModule))
        }
    }
}