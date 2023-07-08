package nl.anwb.assessment.handler

import nl.anwb.assessment.exception.AnwbExceptions
import nl.anwb.assessment.config.Config
import nl.anwb.assessment.service.ClosestBikeParkingService
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.lang.NumberFormatException
import java.util.*

class BikeParkingHandler(
    private val closestBikeParkingService: ClosestBikeParkingService
) {

    suspend fun getClosestBikeParkingToLocation(request: ServerRequest): ServerResponse {

        val long = getQueryParam(request, "long")
        val lat = getQueryParam(request, "lat")

        val orderedBikeParkingByDistance = runCatching {
            closestBikeParkingService.getListOfClosestBikeParkingService(lat, long, Config.bikeParkingDataPath) }
            .onFailure { throw it }
            .getOrThrow()

        return ServerResponse.ok().bodyValueAndAwait(orderedBikeParkingByDistance)
    }

    private suspend fun getQueryParam(request: ServerRequest, paramName: String): Double {
        return request.queryParam(paramName)
            .runCatching { get().toDouble() }
            .onFailure { badRequestResponse(AnwbExceptions.InvalidParameterException(paramName))  }
            .getOrThrow()
    }

    private suspend fun badRequestResponse(exception: RuntimeException): ServerResponse = ServerResponse.badRequest().bodyValueAndAwait(exception)
}
