package ua.com.cuteteam.core.repositories

import ua.com.cuteteam.core.providers.LocationProvider


abstract class MapRepository {
    val locationProvider =
        LocationProvider()
}