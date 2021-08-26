package com.nikodem.crypto.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptoSupplyResponse(
    @Json(name = "status") val status: String,
    @Json(name = "data") val data: SupplyData
)

@JsonClass(generateAdapter = true)
data class SupplyData(
    @Json(name = "supply") val supply: SupplyDetail
)

@JsonClass(generateAdapter = true)
data class SupplyDetail(
    @Json(name = "maxAmount") val maxAmount: String?,
    @Json(name = "totalSyncedAt") val totalSyncedAt: String?,
    @Json(name = "totalAmount") val totalAmount: String?,
    @Json(name = "circulatingSyncedAt") val circulatingSyncedAt: String?,
    @Json(name = "circulatingAmount") val circulatingAmount: String?
)
