package eu.karcags.plugins

import eu.karcags.`language-server`.KotlinLanguageServer
import eu.karcags.utils.getLocalDirectory
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.launch
import java.nio.file.Paths
import java.time.Duration
import java.util.*

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }



    routing {
        webSocket("/ws/language-server/kotlin") {
            val id = UUID.randomUUID().toString()
            val languageServer: KotlinLanguageServer = KotlinLanguageServer(
                Paths.get(getLocalDirectory(), "language-server", "bin", "kotlin-language-server"),
                call.application.environment.log
            ).also {
                it.subscribe(id) {
                    outgoing.send(Frame.Text(it))
                }
            }

            launch(Dispatchers.IO) {
                languageServer.start()
            }

            try {
                for(frame in incoming) {
                    if (frame is Frame.Text) {
                        languageServer.sendMessage(frame.readText())
                    }
                }
            } catch (e: ClosedReceiveChannelException) {
                languageServer.unsubscribe(id)
                languageServer.dispose()
            }
        }
    }
}