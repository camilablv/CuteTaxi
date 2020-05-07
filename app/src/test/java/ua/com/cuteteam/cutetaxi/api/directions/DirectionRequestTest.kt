package ua.com.cuteteam.cutetaxi.api.directions

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test

class DirectionRequestTest {

    private var directionRequest: DirectionRequest? = null

    @Before
    fun init() {
        directionRequest = DirectionRequest()
    }

    @After
    fun close() {
        directionRequest = null
    }

    @Test
    fun getUrl() {
        assertThat(directionRequest?.url, Matchers.equalTo("https://maps.googleapis.com/maps/api/directions/"))
    }

}