package ua.com.cuteteam.core.livedata

import com.google.android.gms.maps.model.LatLng
import ua.com.cuteteam.core.data.MarkerData

sealed class MapAction {
    object UpdateMapObjects : MapAction()
    class CreateMarker(val pair: Pair<String, MarkerData>) : MapAction()
    class RemoveMarker(val tag: String) : MapAction()
    class CreateMarkerByCoordinates(val tag: String, val markerData: MarkerData) : MapAction()
    class AddOnMapClickListener(val callback: ((LatLng) -> Unit)) : MapAction()
    object RemoveOnMapClickListener : MapAction()
    class MoveCamera(val latLng: LatLng) : MapAction()
    class BuildRoute(val from: LatLng, val to: LatLng, val wayPoints: List<LatLng> = emptyList()) :
        MapAction()
    object UpdateCameraForRoute : MapAction()
    class ShowCar(val bearing: Float, val markerData: MarkerData, val from: LatLng, val to: LatLng) : MapAction()
    object ClearMap : MapAction()
}
