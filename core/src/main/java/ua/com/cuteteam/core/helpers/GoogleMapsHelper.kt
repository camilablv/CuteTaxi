package ua.com.cuteteam.core.helpers

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.animation.LinearInterpolator
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import ua.com.cuteteam.core.R
import ua.com.cuteteam.core.data.MarkerData
import ua.com.cuteteam.core.directions.entities.Route
import ua.com.cuteteam.core.providers.DirectionsProvider
import ua.com.cuteteam.core.utils.MapsUtils
import javax.inject.Inject


class GoogleMapsHelper @Inject constructor(
    private val directionsProvider: DirectionsProvider,
    private val utils: MapsUtils
) {

    private lateinit var googleMap: GoogleMap

    private var currentRoute: Route? = null

    fun initMap(map: GoogleMap) {
        googleMap = map
        googleMap.isMyLocationEnabled = true
        googleMap.isBuildingsEnabled = false
    }

    fun updateMarkers(markers: MutableCollection<MarkerData>?) {
        googleMap.clear()
        markers?.forEach { createMarker(it) }
    }

    fun onCameraMove(callback: ((CameraPosition) -> Unit)) {
        googleMap.setOnCameraMoveListener { callback.invoke(googleMap.cameraPosition) }
    }

    fun moveCameraToLocation(latLng: LatLng) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f), 1000, null)
    }

    fun createMarker(markerData: MarkerData): Marker? {
        return googleMap.addMarker(
            MarkerOptions()
                .position(markerData.position)
                .icon(
                    BitmapDescriptorFactory.fromBitmap(
                        utils.resizeMapIcons(markerData.icon, 150, 150)
                    )
                )
        )
    }

    fun removeMarker(marker: Marker?) {
        marker?.remove()
    }

    fun addOnMapClickListener(callback: ((LatLng) -> Unit)) {
        googleMap.setOnMapClickListener { latLng ->
            callback.invoke(latLng)
        }
    }

    suspend fun buildRoute(from: LatLng, to: LatLng, wayPoints: List<LatLng>): Array<LatLng> {
        return RouteHelper(directionsProvider.directions(from, to, wayPoints)).polylinePoints()
    }


    fun createPolyline(polyline: Array<LatLng>): PolylineOptions? {
        val customCap = CustomCap(
            BitmapDescriptorFactory.fromResource(R.drawable.circular_shape_silhouette),
            300f
        )
        val polylineOptions = PolylineOptions()
            .clickable(true)
            .add(*polyline)
            .color(Color.parseColor("#0288d1"))
            .width(15f)
            .startCap(customCap)
            .endCap(customCap)

        addPolyline(polylineOptions)
        return polylineOptions
    }

    fun addPolyline(polylineOptions: PolylineOptions) {
        googleMap.addPolyline(polylineOptions)
    }

    fun removeOnMapClickListener() {
        googleMap.setOnMapClickListener(null)
    }

    fun updateCameraForCurrentRoute(polyline: Array<LatLng>) {
        polyline?.let {
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    buildBoundaryForRoute(it),
                    50
                )
            )
        }
    }

    private fun buildBoundaryForRoute(polyline: Array<LatLng>): LatLngBounds {
        return LatLngBounds.Builder()
            .include(polyline.minBy { it.longitude })
            .include(polyline.minBy { it.latitude })
            .include(polyline.maxBy { it.latitude })
            .include(polyline.maxBy { it.longitude })
            .build()
    }

    fun animateCarOnMap(bearing: Float, markerData: MarkerData, from: LatLng, to: LatLng) {
        val carMarker = googleMap.addMarker(
            MarkerOptions()
                .position(markerData.position)
                .icon(BitmapDescriptorFactory.fromResource(markerData.icon))
        )
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = 1000
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                val animatedFraction = valueAnimator.animatedFraction
                val lat = (animatedFraction * to.latitude) + (1 - animatedFraction) * from.latitude
                val lng =
                    (animatedFraction * to.longitude) + (1 - animatedFraction) * from.longitude

                val newPosition = LatLng(lat, lng)

                carMarker.position = newPosition
                carMarker.setAnchor(0.5f, 0.5f)
                carMarker.rotation = bearing
            }
        })

        valueAnimator.start()
    }
}
