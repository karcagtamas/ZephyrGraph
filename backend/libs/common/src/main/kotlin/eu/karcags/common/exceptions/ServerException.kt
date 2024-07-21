package eu.karcags.common.exceptions

import io.ktor.http.*

open class ServerException(msg: String, val status: HttpStatusCode = HttpStatusCode.InternalServerError) : Exception(msg) {

    class Authentication(msg: String) : ServerException(msg)

    class NotFound : ServerException("Not found", HttpStatusCode.NotFound)
}