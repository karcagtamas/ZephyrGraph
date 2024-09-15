package eu.karcags.ceg.plugins

import eu.karcags.ceg.common.exceptions.ServerException
import eu.karcags.ceg.domain.ErrorData
import eu.karcags.ceg.domain.RequestResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureExceptionHandling() {
    install(StatusPages) {
        exception<ServerException> { call, cause ->
            call.response.status(cause.status)
            call.respond(RequestResult.Error(ErrorData(cause), cause.status.value))
        }
        exception<Throwable> { call, cause ->
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(RequestResult.Error(ErrorData(cause), HttpStatusCode.InternalServerError.value))
        }
    }
}