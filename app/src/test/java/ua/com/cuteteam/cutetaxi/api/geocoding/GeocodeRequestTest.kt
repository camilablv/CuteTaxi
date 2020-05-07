package ua.com.cuteteam.cutetaxi.api.geocoding

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test

class GeocodeRequestTest {

    private var geocodeRequest: GeocodeRequest? = null

    @Before
    fun init() {
        geocodeRequest = GeocodeRequest.Builder().build()
    }

    @After
    fun close() {
        geocodeRequest = null
    }

    @Test
    fun getUrl() {
        MatcherAssert.assertThat(
            geocodeRequest?.url,
            Matchers.equalTo("https://maps.googleapis.com/maps/api/geocode/")
        )
    }
}