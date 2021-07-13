package com.nikodem.crypto.repositories

import com.nikodem.crypto.services.CryptoApiService
import com.nikodem.crypto.services.CryptoResponse

interface CryptoRepository {
    suspend fun getCryptoData(): CryptoResponse
}

class CryptoApiRepository(
    private val cryptoApiService: CryptoApiService
) : CryptoRepository {
    override suspend fun getCryptoData(): CryptoResponse {
        return cryptoApiService.getCryptoData()
    }
}