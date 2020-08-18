package ua.com.cuteteam.core.providers

import com.google.android.gms.maps.model.LatLng
import ua.com.cuteteam.core.directions.DirectionRequest
import ua.com.cuteteam.core.directions.entities.Directions
import javax.inject.Inject

class DirectionsProvider @Inject constructor(private val directionRequest: DirectionRequest) {

    suspend fun directions(from: LatLng, to: LatLng, wayPoints: List<LatLng> = listOf()): Directions {
        return directionRequest.send(DirectionRequest.Query(from, to, wayPoints))
    }
}
