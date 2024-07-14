package eu.karcags.utils

import eu.karcags.domain.RequestResult
import io.ktor.http.*

fun <T> wrapping(data: T, status: HttpStatusCode = HttpStatusCode.OK): RequestResult.Success<T> {
    return RequestResult.Success(data, status.value)
}