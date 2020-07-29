package ua.com.cuteteam.core.providers

import com.google.android.gms.maps.model.LatLng
import ua.com.cuteteam.core.directions.DirectionRequest
import ua.com.cuteteam.core.directions.entities.Directions

class DirectionsProvider(private val directionRequest: DirectionRequest = DirectionRequest()) {

    suspend fun directions(from: LatLng, to: LatLng, wayPoints: List<LatLng> = listOf()): Directions {
        return directionRequest.send(DirectionRequest.Query(from, to, wayPoints))
    }
}
