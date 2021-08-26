package com.nikodem.crypto.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptoDetailResponse(
    @Json(name = "status") val status: String,
    @Json(name = "data") val data: DetailData
)

@JsonClass(generateAdapter = true)
data class DetailData(
    @Json(name = "coin") val coin: DetailCoin = DetailCoin()
)

@JsonClass(generateAdapter = true)
data class DetailCoin(
    @Json(name = "uuid") val uuid: String = "",
    @Json(name = "symbol") val symbol: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "description") val description: String? = "",
    @Json(name = "color") val color: String? = "",
    @Json(name = "iconURL") val iconURL: String = "",
    @Json(name = "websiteURL") val websiteURL: String = "",
    @Json(name = "links") val links: List<Link> = emptyList(),
    @Json(name = "supply") val supply: Supply = Supply(false, "", ""),
    @Json(name = "numberOfMarkets") val numberOfMarkets: Long = 0,
    @Json(name = "numberOfExchanges") val numberOfExchanges: Long = 0,
    @Json(name = "the24HVolume") val the24HVolume: String = "",
    @Json(name = "marketCap") val marketCap: String = "",
    @Json(name = "price") val price: String = "",
    @Json(name = "btcPrice") val btcPrice: String = "",
    @Json(name = "change") val change: String = "",
    @Json(name = "rank") val rank: Long = 0,
    @Json(name = "sparkline") val sparkline: List<String> = emptyList(),
    @Json(name = "allTimeHigh") val allTimeHigh: AllTimeHigh = AllTimeHigh("", 0),
    @Json(name = "coinrankingURL") val coinrankingURL: String? = "",
    @Json(name = "tier") val tier: Long = 0,
    @Json(name = "lowVolume") val lowVolume: Boolean = false
)

@JsonClass(generateAdapter = true)
data class AllTimeHigh(
    @Json(name = "price") val price: String,
    @Json(name = "timestamp") val timestamp: Long
)

@JsonClass(generateAdapter = true)
data class Link(
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: String,
    @Json(name = "url") val url: String
)

@JsonClass(generateAdapter = true)
data class Supply(
    @Json(name = "confirmed") val confirmed: Boolean,
    @Json(name = "total") val total: String?,
    @Json(name = "circulating") val circulating: String?
)

