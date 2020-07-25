package ua.com.cuteteam.core.roads.entities

import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class Roads(
    val snappedPoints: List<Point>
)