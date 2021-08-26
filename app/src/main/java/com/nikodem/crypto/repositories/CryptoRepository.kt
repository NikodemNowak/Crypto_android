package com.nikodem.crypto.repositories

import com.nikodem.crypto.services.CryptoApiService
import com.nikodem.crypto.services.CryptoDetailResponse
import com.nikodem.crypto.services.CryptoResponse
import com.nikodem.crypto.services.CryptoSupplyResponse

interface CryptoRepository {
    var cachedCryptoResponse: CryptoResponse?
    suspend fun getCryptoData(useCacheDataIfPossible: Boolean = true): CryptoResponse
    suspend fun getDetailCryptoData(uuid: String): CryptoDetailResponse
    suspend fun getSupplyCryptoData(uuid: String): CryptoSupplyResponse
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

    override suspend fun getDetailCryptoData(uuid: String): CryptoDetailResponse {
        return cryptoApiService.getDetailCryptoData(uuid)
    }

    override suspend fun getSupplyCryptoData(uuid: String): CryptoSupplyResponse {
        return cryptoApiService.getSupplyCryptoData(uuid)
    }

}