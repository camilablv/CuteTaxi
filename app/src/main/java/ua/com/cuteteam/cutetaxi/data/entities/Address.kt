package ua.com.cuteteam.cutetaxi.data.entities

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class Address(
    @Embedded(prefix = "location_" ) var location: Coordinates? = null,
    var address: String? = null
)