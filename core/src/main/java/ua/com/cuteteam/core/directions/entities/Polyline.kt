package ua.com.cuteteam.core.directions.entities

import com.google.android.gms.maps.model.LatLng
import io.leonard.PolylineUtils

data class Polyline(
    val points: String
) {
    fun toLatLngList() = PolylineUtils.decode(points, 5)
            .map { LatLng(it.latitude, it.longitude) }
}