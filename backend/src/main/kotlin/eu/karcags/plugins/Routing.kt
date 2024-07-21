package eu.karcags.plugins

import eu.karcags.controllers.graphController
import eu.karcags.domain.ErrorData
import eu.karcags.domain.RequestResult
import eu.karcags.common.exceptions.ServerException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    // Configure exception handling
    install(StatusPages) {
        exception<ServerException> { call, cause ->
            call.respond(RequestResult.Error(ErrorData(cause), cause.status.value))
            call.response.status(cause.status)
        }
        exception<Throwable> { call, cause ->
            call.respond(RequestResult.Error(ErrorData(cause), HttpStatusCode.InternalServerError.value))
            call.response.status(HttpStatusCode.InternalServerError)
        }
    }

    // Routes
    routing {
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")

        // API route
        route("/api") {
            graphController()
        }

        // React SPA
        singlePageApplication {
            react("web/dist")
            defaultPage = "index.html"
            useResources = false
        }
    }
}
