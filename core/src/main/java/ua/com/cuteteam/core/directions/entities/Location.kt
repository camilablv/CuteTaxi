package ua.com.cuteteam.core.directions.entities

import com.squareup.moshi.Json

data class Location(
    @Json(name = "lat")
    val latitude: Double,
    @Json(name = "lng")
    val longitude: Double
)