package eu.karcags.ceg.domain

import io.ktor.http.*
import kotlinx.serialization.Serializable

/**
 * Request result
 * @property data request data
 * @property status request status
 * @property success the request is success or not
 * @property error error data
 * @constructor creates a request result
 * @param T the type of the wrapped object
 */
abstract class RequestResult<T> {
    abstract val data: T?
    abstract val status: Int
    abstract val success: Boolean
    abstract val error: ErrorData?

    /**
     * Success request result.
     * @param data request data
     * @param status request status
     */
    @Serializable
    class Success<T>(override val data: T, override val status: Int = HttpStatusCode.OK.value) : RequestResult<T>() {
        override val error: ErrorData? = null
        override val success: Boolean = true
    }

    /**
     * Error request result.
     * @param error error data
     * @param status request status
     */
    @Serializable
    class Error(override val error: ErrorData, override val status: Int = HttpStatusCode.InternalServerError.value) : RequestResult<Any>() {
        override val data: Nothing? = null
        override val success: Boolean = false
    }
}