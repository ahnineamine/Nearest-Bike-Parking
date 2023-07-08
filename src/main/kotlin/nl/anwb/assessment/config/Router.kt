package nl.anwb.assessment.config

import nl.anwb.assessment.handler.BikeParkingHandler
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.coRouter

fun appRouter(bikeParkingHandler: BikeParkingHandler) = coRouter {
    GET(
        "/api/bikeParking",
        RequestPredicates.all()
            .and(queryParam("lat") { true })
            .and(queryParam("long") { true })
        ,
        bikeParkingHandler::getClosestBikeParkingToLocation)
}