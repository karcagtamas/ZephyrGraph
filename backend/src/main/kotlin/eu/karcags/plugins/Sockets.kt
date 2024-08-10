package eu.karcags.plugins

import eu.karcags.`language-server`.KotlinLanguageServer
import eu.karcags.utils.getLocalDirectory
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.nio.file.Paths
import java.time.Duration

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val languageServer = KotlinLanguageServer(
        Paths.get(getLocalDirectory(), "language-server", "bin", "kotlin-language-server"),
        environment.log
    ).also { it.start() }

    routing {
        webSocket("/ws/language-server/kotlin") {
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    outgoing.send(Frame.Text(languageServer.sendMessage(frame.readText())))
                }
            }
        }
    }
}