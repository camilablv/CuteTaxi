package ua.com.cuteteam.cutetaxi.api.directions

import ua.com.cuteteam.cutetaxi.BuildConfig
import ua.com.cuteteam.cutetaxi.api.APIRequest
import ua.com.cuteteam.cutetaxi.api.adapters.LatLngAdapter

/**
 *
 * Do request for directions with this class
 *
 */
class DirectionRequest : APIRequest<DirectionService>() {

    /**
     * Base url for API request. Used by Retrofit
     *
     */
    override val url: String
        get() = BuildConfig.GOOGLE_DIRECTIONS_API_URL

    /**
     * Moshi adapter for convert Location to LatLng
     *
     */
    override val adapter: Any?
        get() = LatLngAdapter()

    /**
     * Suspend function. Does request to the server for direction between two locations
     * @param map is a map of request parameters
     * @return an instance of [Route]
     */
    suspend fun requestDirection(map: Map<String, String>) =
        getService<DirectionService>().getDirection(map)

}