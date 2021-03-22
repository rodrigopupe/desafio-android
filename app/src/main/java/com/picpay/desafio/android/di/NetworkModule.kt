package com.picpay.desafio.android.di

import com.google.gson.GsonBuilder
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.core.CacheInterceptor
import com.picpay.desafio.android.core.NetworkConfig
import com.picpay.desafio.android.data.api.PicPayService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { NetworkConfig(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { providePicPayService(get()) }
}

fun provideOkHttpClient(networkConfig: NetworkConfig): OkHttpClient {
    val cl = OkHttpClient.Builder()
        .cache(networkConfig.getCache())
        .addInterceptor(CacheInterceptor(networkConfig))

    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        cl.addInterceptor(logging)
    }

    return cl.build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    val converter = GsonBuilder().serializeNulls().create()

    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(converter))
        .build()
}

fun providePicPayService(retrofit: Retrofit): PicPayService {
    return retrofit.create(PicPayService::class.java)
}