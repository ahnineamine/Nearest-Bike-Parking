package nl.anwb.assessment.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import io.mockk.every
import io.mockk.mockk
import nl.anwb.assessment.exception.AnwbExceptions
import nl.anwb.assessment.config.appRouter
import nl.anwb.assessment.handler.BikeParkingHandler
import nl.anwb.assessment.service.ClosestBikeParkingService
import org.geojson.FeatureCollection
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

class BikeParkingHandlerTest {

    @Test
    fun bikeParkingHandlerTest() {
        val expectedResult = jacksonObjectMapper().readValue(javaClass.getResource("/bikeParkingTestSample/resultSample.json"), FeatureCollection::class.java)

        val closestBikeParkingService = mockk<ClosestBikeParkingService>()
        every {
            closestBikeParkingService.getListOfClosestBikeParkingService(any(), any(),any())
        } returns expectedResult
        val client = WebTestClient.bindToRouterFunction(appRouter(BikeParkingHandler(closestBikeParkingService))).build()
        val result = client.get()
            .uri("/api/bikeParking?lat=52.07172525242469&long=4.296102536102456")
            .exchange()
            .expectStatus().isOk
            .expectBody<FeatureCollection>()
            .returnResult()
            .responseBody!!

        assertThat("Both collections have same number of features", result.features.size, equalTo(expectedResult.features.size))
        assertThat("the properties for both first features are the same", result.features[0].properties, equalTo(expectedResult.features[0].properties))
        assertThat("the properties for both second features are the same", result.features[1].properties, equalTo(expectedResult.features[1].properties))
        assertThat("the properties for both last features are the same", result.features[2].properties, equalTo(expectedResult.features[2].properties))

    }
}