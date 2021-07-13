package com.nikodem.crypto.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatsResponse (
    @Json(name = "categories") val categories: List<Category>?,
    @Json(name = "id") val id: String,
    @Json(name = "url") val url: String,
    @Json(name = "width") val width: String,
    @Json(name = "height") val height: String
)

@JsonClass(generateAdapter = true)
data class Category (
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String
)
