package com.nikodem.crypto.di

import com.nikodem.crypto.services.CatApiService
import com.nikodem.crypto.services.CryptoApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return@single OkHttpClient.Builder().addInterceptor(logging).build()
    }

    single(named("COIN_RANKING")) {
        Retrofit.Builder()
            .baseUrl("https://api.coinranking.com/v2/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single(named("CAT_API")) {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/images/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single<CryptoApiService> {
        get<Retrofit>(named("COIN_RANKING")).create(CryptoApiService::class.java)
    }

    single<CatApiService> {
        get<Retrofit>(named("CAT_API")).create(CatApiService::class.java)
    }

}