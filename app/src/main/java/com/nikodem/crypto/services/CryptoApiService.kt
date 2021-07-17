package com.nikodem.crypto.services

import retrofit2.http.GET
interface CryptoApiService {
    @GET("coins")
    suspend fun getCryptoData(): CryptoResponse
}