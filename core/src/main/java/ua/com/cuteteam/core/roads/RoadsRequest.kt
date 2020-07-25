package ua.com.cuteteam.core.roads

import com.google.android.gms.maps.model.LatLng

class RoadsRequest(private val roadsService: RoadsService = RoadsServiceBuilder().build()) {

    suspend fun send(coordinates: List<LatLng>) =
        roadsService.roads(latLngListToString(coordinates))

    private fun latLngListToString(list: List<LatLng>) = list.joinToString("|") {
        "${it.latitude},${it.longitude}"
    }
}

