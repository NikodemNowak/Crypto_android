package com.nikodem.crypto.services

import retrofit2.http.GET

interface CatApiService {
    @GET("search")
    suspend fun getRandomCat(): List<CatsResponse>
}