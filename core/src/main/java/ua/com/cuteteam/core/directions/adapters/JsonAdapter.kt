package ua.com.cuteteam.core.directions.adapters

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import ua.com.cuteteam.core.directions.entities.Location

class JsonAdapter {
    @FromJson
    fun fromJson(location: Location): LatLng {
        return LatLng(location.latitude, location.longitude)
    }

    @ToJson
    fun toJson(latLng: LatLng): Location {
        return Location(latLng.latitude, latLng.longitude)
    }
}