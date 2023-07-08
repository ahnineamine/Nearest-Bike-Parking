package nl.anwb.assessment.exception

class AnwbExceptions {

    class InvalidFileException(filePath: String): AnwbException(
        "The file that you are trying to read is invalid",
        mapOf("invalidFile" to filePath),
        "INVALID_FILE_EXCEPTION"
    )

    class InvalidParameterException(parameterName:String): AnwbException(
        "The following parameter is invalid",
        mapOf("parameterName" to parameterName),
        "INVALID_PARAMETER"
    )
}