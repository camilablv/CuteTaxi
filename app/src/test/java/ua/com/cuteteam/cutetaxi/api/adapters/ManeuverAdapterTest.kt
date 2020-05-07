package ua.com.cuteteam.cutetaxi.api.adapters

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import ua.com.cuteteam.cutetaxi.api.directions.Maneuver

class ManeuverAdapterTest {

    private var adapter: ManeuverAdapter? = null
    private val testedValue =
        Maneuver.FERRY

    @Before
    fun init() {
        adapter = ManeuverAdapter()
    }

    @After
    fun close() {
        adapter = null
    }

    @Test
    fun toJson() {

        val actual = testedValue.maneuver
        val result = adapter?.toJson(testedValue)

        assertThat(actual, Matchers.equalTo(result))

    }

    @Test
    fun fromJson() {

        val actual = testedValue
        val result = adapter?.fromJson(testedValue.maneuver)

        assertThat(actual, Matchers.equalTo(result))

    }
}