package ua.com.cuteteam.core.providers

import com.google.android.gms.maps.model.LatLng
import ua.com.cuteteam.core.directions.DirectionRequest
import ua.com.cuteteam.core.directions.entities.Directions
import ua.com.cuteteam.core.directions.entities.Route
import ua.com.cuteteam.core.roads.RoadsRequest

class RouteProvider(
    private val directionRequest: DirectionRequest = DirectionRequest(),
    private val roadsRequest: RoadsRequest = RoadsRequest()
) {

    suspend fun routes(from: LatLng, to: LatLng, wayPoints: List<LatLng> = listOf()): List<Route> {
        val directions = directionRequest.send(DirectionRequest.Query(from, to, wayPoints))
        return directions.routes
    }
}
