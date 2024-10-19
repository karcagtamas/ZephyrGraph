package eu.karcags.ceg.plugins

import eu.karcags.ceg.utils.getIntProperty
import eu.karcags.ceg.utils.getStringProperty
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
    val clientHost = getStringProperty(environment.config, "client.external.host", "localhost")
    val clientPort = getIntProperty(environment.config, "client.external.port", 3001)
    install(CORS) {
        allowHost("$clientHost:$clientPort")
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }
}