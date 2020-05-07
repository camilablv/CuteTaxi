package ua.com.cuteteam.cutetaxi.api.roads

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Test

import org.junit.Before

class RoadsTest {

    private var roadsRequest: RoadsRequest? = null

    @Before
    fun init() {
        roadsRequest = RoadsRequest()
    }

    @After
    fun close() {
        roadsRequest = null
    }

    @Test
    fun getUrl() {
        MatcherAssert.assertThat(
            roadsRequest?.url,
            Matchers.equalTo("https://roads.googleapis.com/v1/")
        )
    }

}