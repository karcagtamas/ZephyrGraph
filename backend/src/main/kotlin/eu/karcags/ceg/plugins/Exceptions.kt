package eu.karcags.ceg.plugins

import eu.karcags.ceg.common.exceptions.ServerException
import eu.karcags.ceg.domain.ErrorData
import eu.karcags.ceg.domain.RequestResult
import eu.karcags.ceg.graphmodel.exceptions.GraphException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import javax.script.ScriptException

fun Application.configureExceptionHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            if (cause is ServerException) {
                call.response.status(cause.status)
                call.respond(RequestResult.Error(ErrorData(cause), cause.status.value))
            } else if (cause is ScriptException && cause.cause is GraphException) {
                call.response.status(HttpStatusCode.InternalServerError)
                call.respond(RequestResult.Error(ErrorData(cause.cause!!), HttpStatusCode.InternalServerError.value))
            } else {
                call.response.status(HttpStatusCode.InternalServerError)
                call.respond(RequestResult.Error(ErrorData(cause), HttpStatusCode.InternalServerError.value))
            }
        }
    }
}