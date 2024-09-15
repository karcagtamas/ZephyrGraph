package eu.karcags.ceg.plugins

import eu.karcags.ceg.controllers.graphController
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureAPIRouting() {
    routing {
        // API route
        route("/api") {
            graphController()
        }
    }
}

fun Application.configureStaticRouting() {
    routing {
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")

        // React SPA
        singlePageApplication {
            react("web/dist") // TODO: Config
            defaultPage = "index.html"
            useResources = false
        }
    }
}