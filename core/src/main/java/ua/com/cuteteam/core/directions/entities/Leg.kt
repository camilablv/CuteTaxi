package ua.com.cuteteam.core.directions.entities

data class Leg(
    val steps: List<Step>,
    val duration: Duration,
    val distance: Distance
)