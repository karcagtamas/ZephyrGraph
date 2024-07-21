package eu.karcags.utils

import eu.karcags.domain.RequestResult
import io.ktor.http.*

fun <T> T.wrapping(status: HttpStatusCode = HttpStatusCode.OK): RequestResult.Success<T> {
    return RequestResult.Success(this, status.value)
}