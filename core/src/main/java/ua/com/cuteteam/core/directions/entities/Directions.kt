package ua.com.cuteteam.core.directions.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Directions(
    val status: String,
    val routes: List<Route>
)