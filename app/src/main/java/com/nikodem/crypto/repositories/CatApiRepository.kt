package com.nikodem.crypto.repositories

import com.nikodem.crypto.services.CatApiService
import com.nikodem.crypto.services.CatsResponse

interface CatRepository {
    suspend fun getRandomCat(): CatsResponse
}

class CatApiRepository(
    private val catApiService: CatApiService
) : CatRepository {
    override suspend fun getRandomCat(): CatsResponse {
        return catApiService.getRandomCat()[0]
    }
}