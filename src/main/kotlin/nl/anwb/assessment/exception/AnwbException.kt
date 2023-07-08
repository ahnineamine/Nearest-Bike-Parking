package nl.anwb.assessment.exception

open class AnwbException(
    message: String,
    val additionalProperties: Map<String, Any?> = emptyMap(),
    val code: String? = null
) : RuntimeException(message)