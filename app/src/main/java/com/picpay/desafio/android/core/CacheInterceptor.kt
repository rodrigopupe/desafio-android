package com.picpay.desafio.android.core

import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor(private val networkConfig: NetworkConfig) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = if (networkConfig.connected)
            chain.request().newBuilder()
                .header("Cache-Control", "public, max-age=" + 5) // 5 segundos
                .build()
        else
            chain.request().newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 1) // 1 day
                .build()
        return chain.proceed(request)
    }
}