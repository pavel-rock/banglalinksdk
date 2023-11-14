package com.rockstreamer.iscreensdk.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

//
//class IScreenInterceptor: Interceptor {
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        var request: Request = chain.request()
//        val builder: Request.Builder = request.newBuilder()
//        builder.header("Accept", "application/json")
//        builder.header("Origin", "https://iscreen.com.bd")
//        builder.header("AppVersion", "banglalink_my_bl")
//
//        if (!loginState.getString(API_TOKEN, "").isNullOrEmpty()) {
//            builder.header("Authorization", "Bearer " + loginState.getString(API_TOKEN , ""))
//        }
//
//        request = builder.build()
//        return chain.proceed(request)
//    }
//}