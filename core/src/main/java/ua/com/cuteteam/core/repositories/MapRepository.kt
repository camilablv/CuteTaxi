package ua.com.cuteteam.core.repositories

import ua.com.cuteteam.core.providers.LocationProvider
import javax.inject.Inject

open class MapRepository @Inject constructor(val locationProvider: LocationProvider) {

}
