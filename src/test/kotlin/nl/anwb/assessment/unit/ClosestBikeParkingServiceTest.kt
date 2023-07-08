package nl.anwb.assessment.unit

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.natpryce.hamkrest.assertion.assertThat
import nl.anwb.assessment.exception.AnwbExceptions
import nl.anwb.assessment.service.ClosestBikeParkingService
import com.natpryce.hamkrest.containsSubstring
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.isA
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ClosestBikeParkingServiceTest {

    @Test
    fun verifyOrderOfBikeParking(){

        val closestBikeParkingService = ClosestBikeParkingService(jacksonObjectMapper())
        val featureCollection = closestBikeParkingService.getListOfClosestBikeParkingService(52.07172525242469, 4.296102536102456, "/bikeParkingTestSample/fietsenstallingenSample.json")

        // A sample of two Parking (ALTINGSTRAAT 3, BOSSCHESTRAAT 58). The given location is Jacobastraat 198.

        assertThat("Closest Parking is in ALTINGSTRAAT 3", featureCollection.features[0].properties["STRAAT"]!! == "ALTINGSTRAAT 3")
        assertThat("The order of closest parking should be 1", featureCollection.features[0].properties["ORDER"]!! == 1)

        assertThat("Furthest Parking is in BOSSCHESTRAAT 58", featureCollection.features[1].properties["STRAAT"]!! == "BOSSCHESTRAAT 58")
        assertThat("The order of the furthest parking should be 2", featureCollection.features[1].properties["ORDER"]!! == 2)

    }

    @Test
    fun throwWInvalidFileException(){
        val closestBikeParkingService = ClosestBikeParkingService(jacksonObjectMapper())

        val invalidFilePath = "/bikeParkingTestSample/test.json"

        val exception = assertThrows<AnwbExceptions.InvalidFileException> {
            closestBikeParkingService.getListOfClosestBikeParkingService(
                52.07172525242469,
                4.296102536102456,
                invalidFilePath
            )
        }

        assertThat(exception, isA(AnwbExceptions.InvalidFileException::class.java))
        assertThat(exception.message!!, containsSubstring("The file that you are trying to read is invalid"))
        assertTrue(exception.additionalProperties == mapOf("invalidFile" to invalidFilePath))
    }
}