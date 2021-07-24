package com.nikodem.crypto.repositories

import com.nikodem.crypto.services.CryptoApiService
import com.nikodem.crypto.services.CryptoResponse

interface CryptoRepository {
    var cachedCryptoResponse: CryptoResponse?
    suspend fun getCryptoData(useCacheDataIfPossible: Boolean = true): CryptoResponse
}

class CryptoApiRepository(
    private val cryptoApiService: CryptoApiService,
) : CryptoRepository {
    override var cachedCryptoResponse: CryptoResponse? = null

    override suspend fun getCryptoData(useCacheDataIfPossible: Boolean): CryptoResponse {
        return if (cachedCryptoResponse != null && useCacheDataIfPossible) cachedCryptoResponse!! else {
            val response = cryptoApiService.getCryptoData()
            cachedCryptoResponse = response
            return response
        }
    }
}