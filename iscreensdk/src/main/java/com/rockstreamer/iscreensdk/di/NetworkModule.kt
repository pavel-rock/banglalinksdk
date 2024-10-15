package com.rockstreamer.iscreensdk.di


import android.content.Context
import com.rockstreamer.iscreensdk.IScreenActivity
import com.rockstreamer.iscreensdk.api.api.ApiHelperImpl
import com.rockstreamer.iscreensdk.api.NetworkHelper
import com.rockstreamer.iscreensdk.api.api.ApiHelper
import com.rockstreamer.iscreensdk.api.api.ApiService
import com.rockstreamer.iscreensdk.utils.API_TOKEN
import com.rockstreamer.iscreensdk.utils.BASE_URL

import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get() , "https://dev-api.rockstreamer.com/") }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }
    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }
}

private fun provideOkHttpClient(): OkHttpClient{
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder().addInterceptor(loggingInterceptor).connectTimeout(5, TimeUnit.MINUTES).writeTimeout(5 , TimeUnit.MINUTES).readTimeout(5 , TimeUnit.MINUTES).build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit =
    Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
private fun provideNetworkHelper(context: Context) = NetworkHelper(context)
