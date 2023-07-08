package nl.anwb.assessment.unit

import nl.anwb.assessment.helpers.Haversine
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class HaversineDistanceTest {


    companion object {
        @JvmStatic
        fun provideCoordinates(): List<Arguments> = listOf(
            Arguments.of(52.07172525242469,4.296102536102456,52.08581472047199,4.343657213759458,3.608),
            Arguments.of(52.07172525242469,4.296102536102456,52.111833879188495,4.288289541521275,4.492),
            Arguments.of(52.07172525242469,4.296102536102456,52.0758546674018,4.2594362754591,2.548)
        )
    }

    @ParameterizedTest
    @MethodSource("provideCoordinates")
    fun testHaversineDistance(
        fromLat: Double, fromLon: Double, toLat: Double, toLon: Double, expectedResult: Double
    ){
        val distance = Haversine.distance(fromLat, fromLon, toLat, toLon)
        assertTrue(distance.equals(expectedResult))
    }
}