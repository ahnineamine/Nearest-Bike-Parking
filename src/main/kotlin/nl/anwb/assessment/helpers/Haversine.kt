package nl.anwb.assessment.helpers

import kotlin.math.*

object Haversine {

    private const val EARTH_RADIUS = 6371

    fun distance(fromLat: Double, fromLon: Double, toLat: Double, toLon: Double): Double {

        val latDistance = Math.toRadians(toLat - fromLat)
        val lonDistance = Math.toRadians(toLon - fromLon)
        val a = sin(latDistance / 2) * sin(latDistance / 2) +
                cos(Math.toRadians(fromLat)) * cos(Math.toRadians(toLat)) *
                sin(lonDistance / 2) * sin(lonDistance / 2);
        val c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return (EARTH_RADIUS * c * 1000).roundToInt() / 1000.0
    }
}