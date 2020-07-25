package ua.com.cuteteam.core.roads

import retrofit2.http.GET
import retrofit2.http.Query
import ua.com.cuteteam.core.roads.entities.Roads

interface RoadsService {
    @GET(value = "snapToRoads")
    suspend fun roads(
        @Query("path") path: String,
        @Query("interpolate") interpolate: Boolean = true
    ): Roads
}

