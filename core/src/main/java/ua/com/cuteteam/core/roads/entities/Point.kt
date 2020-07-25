package ua.com.cuteteam.core.roads.entities

import com.google.android.gms.maps.model.LatLng

data class Point @ExperimentalUnsignedTypes constructor(
    val location: LatLng,
    val originalIndex: Int
)

//data class Location(
//    val latitude: Float,
//    val longitude: Float
//)