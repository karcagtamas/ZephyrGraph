package eu.karcags.ceg.plugins

import eu.karcags.ceg.controllers.graphController
import eu.karcags.ceg.utils.getStringProperty
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
        val clientPath = getStringProperty(environment?.config, "client.internal.path", "web/dist")

        // React SPA
        singlePageApplication {
            react(clientPath)
            defaultPage = "index.html"
            useResources = false
        }
    }
}