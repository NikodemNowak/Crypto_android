package com.nikodem.crypto.services

import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoApiService {
    @GET("coins")
    suspend fun getCryptoData(): CryptoResponse

    @GET("coin/{uuid}")
    suspend fun getDetailCryptoData(@Path("uuid") uuid: String): CryptoDetailResponse

    @GET("coin/{uuid}/supply")
    suspend fun getSupplyCryptoData(@Path("uuid") uuid: String): CryptoSupplyResponse
}