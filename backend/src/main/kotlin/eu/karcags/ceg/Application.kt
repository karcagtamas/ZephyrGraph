package eu.karcags.ceg

import eu.karcags.ceg.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080) { // TODO: Load from config
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureOpenAPI()
    configureExceptionHandling()
    configureAPIRouting()
    configureStaticRouting()
    configureCORS()
}
