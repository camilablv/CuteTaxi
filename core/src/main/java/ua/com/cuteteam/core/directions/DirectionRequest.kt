package ua.com.cuteteam.core.directions

import com.google.android.gms.maps.model.LatLng
import ua.com.cuteteam.core.directions.entities.Directions

class DirectionRequest(private val directionService: DirectionService = DirectionServiceBuilder().build()) {

    suspend fun send(query: Query): Directions {
        return directionService.directions(query.toMap())
    }

    data class Query(val from: LatLng, val to: LatLng, val wayPoints: List<LatLng> = listOf()) {
        fun toMap(): Map<String, String> = mapOf(
            "origin" to latLngToString(from),
            "destination" to latLngToString(to),
            "waypoints" to wayPoints.map(::latLngToString).joinToString("|")
        ).filterNot { it.value.isEmpty() }

        fun latLngToString(latLng: LatLng) = "${latLng.latitude},${latLng.longitude}"
    }
}
