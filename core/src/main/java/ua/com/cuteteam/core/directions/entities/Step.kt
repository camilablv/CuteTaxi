package ua.com.cuteteam.core.directions.entities

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json

data class Step(
    @Json(name = "start_location")
    val startLocation: LatLng,
    @Json(name = "end_location")
    val endLocation: LatLng,
    val duration: Duration,
    val distance: Distance,
    @Json(name = "html_instructions")
    val instructions: String
)