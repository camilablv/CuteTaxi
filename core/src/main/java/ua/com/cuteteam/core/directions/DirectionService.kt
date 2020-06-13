package ua.com.cuteteam.core.directions

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ua.com.cuteteam.core.directions.entities.Directions

interface DirectionService {

    @GET(value = "json")
    suspend fun directions(@QueryMap(encoded = true) map: Map<String, String>) : Directions
}