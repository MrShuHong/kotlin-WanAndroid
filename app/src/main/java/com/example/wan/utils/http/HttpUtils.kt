package com.example.wan.utils.http

import com.example.wan.WanApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object  HttpUtils   {

    private var okHttpClient = OkHttpClient.Builder()
        .build()

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun getApi():WanApiService{
        return retrofit.create(WanApiService::class.java)
    }
}