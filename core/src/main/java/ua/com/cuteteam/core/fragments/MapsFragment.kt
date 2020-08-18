package ua.com.cuteteam.core.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.gms.maps.*
import kotlinx.coroutines.*
import pub.devrel.easypermissions.AfterPermissionGranted
import ua.com.cuteteam.core.helpers.GoogleMapsHelper
import ua.com.cuteteam.core.livedata.MapAction
import ua.com.cuteteam.core.permissions.AccessFineLocationPermission
import ua.com.cuteteam.core.permissions.PermissionProvider
import ua.com.cuteteam.core.R
import ua.com.cuteteam.core.dialogs.InfoDialog
import ua.com.cuteteam.core.extentions.injectViewModel
import ua.com.cuteteam.core.extentions.toLatLng
import ua.com.cuteteam.core.permissions.PermissionProviderHostFragment
import ua.com.cuteteam.core.viewmodels.MapViewModel
import ua.com.cuteteam.core.viewmodels.viewmodelfactories.MapViewModelFactory
import javax.inject.Inject


abstract class MapsFragment : SupportMapFragment(), OnMapReadyCallback {

    companion object {
        private var shouldShowPermissionPermanentlyDeniedDialog = true
    }

    @Inject
    lateinit var accessFineLocationPermission: AccessFineLocationPermission

    @Inject
    lateinit var googleMapsHelper: GoogleMapsHelper

    @Inject
    lateinit var mapViewModelFactory: MapViewModelFactory

    abstract var viewModel: MapViewModel

    @Inject
    lateinit var permissionProvider: PermissionProvider

    private lateinit var mMap: GoogleMap

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel = injectViewModel(mapViewModelFactory)

        permissionProvider.apply {
            permissionProviderHost = PermissionProviderHostFragment(this@MapsFragment)

            onDenied = { permission, isPermanentlyDenied ->
                if (isPermanentlyDenied && shouldShowPermissionPermanentlyDeniedDialog) {
                    InfoDialog.show(
                        childFragmentManager,
                        permission.requiredPermissionDialogTitle,
                        permission.requiredPermissionDialogMessage
                    ) { shouldShowPermissionPermanentlyDeniedDialog = false }
                }
            }

            onGranted = {
                if (viewModel.shouldShowGPSRationale())
                    InfoDialog.show(
                        childFragmentManager,
                        getString(R.string.enable_gps_recommended_dialog_title),
                        getString(R.string.enable_gps_recommended_dialog_message)
                    )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        GlobalScope.launch(Dispatchers.Main) {
            initMapData()
        }
    }

    @AfterPermissionGranted(PermissionProvider.LOCATION_REQUEST_CODE)
    private suspend fun initMapData() {
        ::mMap.isInitialized || return

        permissionProvider?.withPermission(accessFineLocationPermission) {
            googleMapsHelper.initMap(mMap)

            initMap(googleMapsHelper)

            viewModel.markersData.observe(this@MapsFragment, Observer {
                googleMapsHelper.updateMarkers(it?.values)
                viewModel.buildRoute()
            })

            viewModel.mapAction.observe(this@MapsFragment, Observer { mapAction ->
                when (mapAction) {
                    is MapAction.UpdateMapObjects -> {
                        googleMapsHelper.updateMarkers(viewModel.markersData.value?.values)
                        if (viewModel.polylineOptions != null)
                            googleMapsHelper.addPolyline(viewModel.polylineOptions!!)
                    }
                    is MapAction.CreateMarkerByCoordinates -> viewModel
                        .setMarkersData(mapAction.tag to mapAction.markerData)

                    is MapAction.CreateMarker -> {
                        val marker = googleMapsHelper.createMarker(mapAction.pair.second)
                        viewModel.setMarkers(mapAction.pair.first to marker)
                        viewModel.setMarkersData(mapAction.pair)
                    }
                    is MapAction.RemoveMarker -> {
                        googleMapsHelper.removeMarker(viewModel.findMarkerByTag(mapAction.tag))
                        viewModel.removeMarker(mapAction.tag)
                    }
                    is MapAction.AddOnMapClickListener -> googleMapsHelper.addOnMapClickListener(
                        mapAction.callback
                    )
                    is MapAction.RemoveOnMapClickListener -> googleMapsHelper.removeOnMapClickListener()
                    is MapAction.BuildRoute -> GlobalScope.launch(Dispatchers.Main) {
                        buildRoute(googleMapsHelper, mapAction)
                    }
                    is MapAction.MoveCamera -> GlobalScope.launch(Dispatchers.Main) {
                        googleMapsHelper.moveCameraToLocation(mapAction.latLng)
                    }
                    is MapAction.UpdateCameraForRoute -> {
                        viewModel.currentRoute.observe(this, Observer {
                            if (it != null)
                                googleMapsHelper.updateCameraForCurrentRoute(it.polyline.toLatLngList().toTypedArray())
                        })
                    }
                    is MapAction.ShowCar -> {
                        googleMapsHelper.animateCarOnMap(
                            mapAction.bearing,
                            mapAction.markerData,
                            mapAction.from,
                            mapAction.to)
                    }
                    is MapAction.ClearMap -> mMap.clear()
                }
            })

            GlobalScope.launch(Dispatchers.Main) {
                if (viewModel.cameraPosition == null)
                    googleMapsHelper.moveCameraToLocation(
                        viewModel.locationProvider.getLocation()?.toLatLng() ?: return@launch
                    )
            }

            googleMapsHelper.onCameraMove { position ->
                viewModel.cameraPosition = position
            }
        }
    }

    private suspend fun buildRoute(
        googleMapsHelper: GoogleMapsHelper,
        mapAction: MapAction.BuildRoute
    ) {
        val polylinePoints = googleMapsHelper.buildRoute(
            mapAction.from,
            mapAction.to,
            mapAction.wayPoints
        )

        viewModel.polylineOptions = googleMapsHelper.createPolyline(polylinePoints)
    }

    abstract fun initMap(googleMapsHelper: GoogleMapsHelper)

    override fun onResume() {
        super.onResume()
        viewModel.updateMapObjects()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionProvider?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
