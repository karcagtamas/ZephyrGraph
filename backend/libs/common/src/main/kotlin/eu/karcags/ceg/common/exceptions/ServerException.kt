package eu.karcags.ceg.common.exceptions

import io.ktor.http.*

/**
 * Server exception model.
 *
 * @property status the status code of the exception
 * @constructor creates a common server exception
 * @param msg the exception message
 * @param status the status code of the exception
 */
open class ServerException(msg: String, val status: HttpStatusCode = HttpStatusCode.InternalServerError) : Exception(msg) {

    /**
     * Authentication server exception.
     *
     * @constructor creates an authentication exception.
     * @param msg the exception message.
     */
    class Authentication(msg: String) : ServerException(msg)

    /**
     *  Not found server exception.
     *
     *  @constructor creates a not found exception.
     */
    class NotFound : ServerException("Not found", HttpStatusCode.NotFound)
}