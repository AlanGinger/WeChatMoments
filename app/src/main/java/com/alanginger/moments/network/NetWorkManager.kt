package com.alanginger.moments.network

import com.alanginger.moments.Constants.Companion.BASE_URL
import com.alibaba.fastjson.support.retrofit.Retrofit2ConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object NetWorkManager {
    // 初始化okhttp
    private var client: OkHttpClient

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()
        HTTPSCerUtils.setTrustAllCertificate(okHttpClientBuilder)
        client = okHttpClientBuilder.build()
    }

    // 初始化Retrofit
    val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(Retrofit2ConverterFactory())
        .build()
}