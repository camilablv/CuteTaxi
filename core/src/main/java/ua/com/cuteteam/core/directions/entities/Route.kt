package ua.com.cuteteam.core.directions.entities

import com.squareup.moshi.Json

data class Route(
    val summary: String,
    val legs: List<Leg>,
    @Json(name = "overview_polyline")
    val polyline: Polyline
) {

}
