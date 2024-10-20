package eu.karcags.ceg.plugins

import eu.karcags.ceg.controllers.graphController
import eu.karcags.ceg.utils.getBooleanProperty
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
        val useResourcesParam = getBooleanProperty(environment?.config, "client.internal.useResources", false)
        val index = getStringProperty(environment?.config, "client.internal.index", "index.html")

        // React SPA
        singlePageApplication {
            react(clientPath)
            defaultPage = index
            useResources = useResourcesParam
        }
    }
}