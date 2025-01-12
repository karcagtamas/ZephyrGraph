package eu.karcags.ceg.domain

import kotlinx.serialization.Serializable

/**
 * Error data.
 * @property cause the cause of the error
 * @property stackTrace the error stack trace
 * @constructor creates an error data
 * @param cause the cause of the error
 * @param stackTrace the error stack trace
 */
@Serializable
data class ErrorData(val cause: String?, val stackTrace: List<String>) {

    /**
     * creates an error data from a throwable
     * @param throwable the source throwable
     */
    constructor(throwable: Throwable) : this(throwable.message, throwable.stackTrace.toList().map { it.toString() })
}