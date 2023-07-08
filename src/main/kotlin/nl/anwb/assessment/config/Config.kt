package nl.anwb.assessment.config

import java.net.URL

object Config {

    const val bikeParkingDataPath = "/bikeParkingData/fietsenstallingen.json"

    fun getFromResource(filePath: String): URL? = javaClass.getResource(filePath)
}