package nl.anwb.assessment.service

import com.fasterxml.jackson.databind.ObjectMapper
import nl.anwb.assessment.config.Config
import nl.anwb.assessment.exception.AnwbExceptions
import nl.anwb.assessment.helpers.Haversine
import org.geojson.*
import org.geotools.geometry.jts.JTS
import org.geotools.referencing.CRS
import org.geotools.referencing.crs.DefaultGeographicCRS
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory


class ClosestBikeParkingService(
    private val objectMapper: ObjectMapper
) {

    /**
     * Get a list of available Bike Parking ordered from closest to furthest (by distance) from a given location
     * @param lat Latitude of the location to measure the distance from
     * @param long Longitude of the location to measure the distance from
     * @param dataSourcePath path of file that has bike parking data
     * @return [FeatureCollection] containing the bike parking ordered by distance from the given location
     */

    fun getListOfClosestBikeParkingService(lat: Double, long: Double, dataSourcePath: String): FeatureCollection {

        val geometryFactory = GeometryFactory()
        val requestedLocation = geometryFactory.createPoint(Coordinate(long,lat))

        val bikeParkingFeatures = getBikeParkingFeatureCollection(dataSourcePath)

        val bikeParkingWithValidCoordinates = bikeParkingFeatures.features
            .filterNot {
                val coordinates = (it.geometry as Point).coordinates
                coordinates.latitude == 0.0 ||coordinates.longitude == 0.0
            }

        val bikeParkingSortedByDistance= bikeParkingWithValidCoordinates.sortedBy { feature ->
            val coordinates = (feature.geometry as Point).coordinates
            val transformedParkingLocation = transformGeometryCRS(geometryFactory, coordinates.longitude, coordinates.latitude)
            val distance = Haversine.distance(requestedLocation.y, requestedLocation.x,transformedParkingLocation.y, transformedParkingLocation.x)
            feature.properties.putIfAbsent("DISTANCE", mapOf("VALUE" to distance, "UNIT" to "KM", "METHODE" to "HAVERSINE"))
            distance
        }

        // Create a new feature collection containing the sorted features
        val featureCollectionOfOrderedBikeParking = FeatureCollection()
        bikeParkingSortedByDistance.forEachIndexed { index, feature ->
            feature.properties.putIfAbsent("ORDER", index + 1)
            featureCollectionOfOrderedBikeParking.add(feature)
        }

        return featureCollectionOfOrderedBikeParking
    }

    private fun getBikeParkingFeatureCollection(filePath: String): FeatureCollection = try {
        objectMapper.readValue(Config.getFromResource(filePath), FeatureCollection::class.java)
    } catch (e: Exception) {
        throw AnwbExceptions.InvalidFileException(filePath)
    }

    /**
     * Convert Coordinates from EPSG:28992 to the WGS84 (EPSG:4326)
     */
    private fun transformGeometryCRS(
        geometryFactory: GeometryFactory,
        longitude: Double,
        latitude: Double
    ): org.locationtech.jts.geom.Point {
        val parkingLocation = geometryFactory.createPoint(Coordinate(longitude, latitude))
        val transform = CRS.findMathTransform(CRS.decode("EPSG:28992"), DefaultGeographicCRS.WGS84)
        return JTS.transform(parkingLocation, transform) as org.locationtech.jts.geom.Point
    }
}
