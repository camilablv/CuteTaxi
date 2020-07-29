package ua.com.cuteteam.core.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PolylineOptions
import ua.com.cuteteam.core.data.MarkerData
import ua.com.cuteteam.core.extentions.findBy
import ua.com.cuteteam.core.extentions.mutation
import ua.com.cuteteam.core.livedata.MapAction
import ua.com.cuteteam.core.livedata.SingleLiveEvent
import ua.com.cuteteam.core.providers.LocationProvider
import ua.com.cuteteam.core.providers.DirectionsProvider
import ua.com.cuteteam.core.repositories.MapRepository

abstract class MapViewModel(private val repository: MapRepository) : ViewModel() {

    var cameraPosition: CameraPosition? = null

    var polylineOptions: PolylineOptions? = null

    var currentRoute = MutableLiveData<DirectionsProvider.RouteSummary>()

    val mapAction = SingleLiveEvent<MapAction>()

    val markersData = MutableLiveData(mutableMapOf<String, MarkerData>())

    private val markers = mutableMapOf<String, Marker?>()

    private var dialogShowed = false

    private val isGPSEnabled get() = repository.locationProvider.isGPSEnabled()

    fun shouldShowGPSRationale(): Boolean {
        if (dialogShowed || isGPSEnabled) return false

        dialogShowed = true
        return true
    }

    val locationProvider: LocationProvider
        get() = repository.locationProvider

    fun setCurrentRoute(routeSummary: DirectionsProvider.RouteSummary) {
        currentRoute.value = routeSummary
    }

    fun setMarkers(pair: Pair<String, Marker?>) {
        markers[pair.first] = pair.second
    }

    fun removeMarker(tag: String) {
        markers.remove(tag)
        markersData.mutation { it.value?.remove(tag) }
    }

    fun findMarkerByTag(tag: String): Marker? {
        return markers.findBy { it.key == tag }?.value
    }

    fun updateMapObjects() {
        mapAction.value = MapAction.UpdateMapObjects
    }

    fun updateCameraForRoute() {
        mapAction.value = MapAction.UpdateCameraForRoute
    }

    fun moveCamera(latLng: LatLng) {
        mapAction.value = MapAction.MoveCamera(latLng)
    }

    fun setMarkersData(pair: Pair<String, MarkerData>) {
        if (findMarkerDataByTag(pair.first)?.equals(pair.second) == true) return
        markersData.value = markersData.value?.plus(pair)?.toMutableMap()
    }

    fun buildRoute() {
        val from = findMarkerDataByTag("A")?.position
        val to = findMarkerDataByTag("B")?.position
        if (from == null || to == null) return
        mapAction.value = MapAction.BuildRoute(from, to)
    }

    private fun findMarkerDataByTag(tag: String): MarkerData? {
        return markersData.value?.findBy { it.key == tag }?.value
    }
}