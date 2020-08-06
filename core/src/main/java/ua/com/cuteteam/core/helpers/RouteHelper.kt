package ua.com.cuteteam.core.helpers

import com.google.android.gms.maps.model.LatLng
import ua.com.cuteteam.core.directions.entities.Directions

class RouteHelper(val directions: Directions) {

    fun polylinePoints(): Array<LatLng> {
        return directions.routes[0].polyline.toLatLngList().toTypedArray()
    }

    fun routeSummary() {
        directions.routes[0].summary
    }
}
