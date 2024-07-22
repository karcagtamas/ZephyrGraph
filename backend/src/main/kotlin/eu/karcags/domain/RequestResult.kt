package eu.karcags.domain

import io.ktor.http.*
import kotlinx.serialization.Serializable

abstract class RequestResult<T> {
    abstract val data: T?
    abstract val status: Int
    abstract val success: Boolean
    abstract val error: ErrorData?

    @Serializable
    class Success<T>(override val data: T, override val status: Int = HttpStatusCode.OK.value) : RequestResult<T>() {
        override val error: ErrorData? = null
        override val success: Boolean = true
    }

    @Serializable
    class Error(override val error: ErrorData, override val status: Int = HttpStatusCode.InternalServerError.value) : RequestResult<Any>() {
        override val data: Nothing? = null
        override val success: Boolean = false
    }
}