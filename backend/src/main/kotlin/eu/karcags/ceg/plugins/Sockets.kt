package eu.karcags.ceg.plugins

import eu.karcags.ceg.languageServer.KotlinLanguageServer
import eu.karcags.ceg.languageServer.validateResponseLine
import eu.karcags.ceg.utils.getLocalDirectory
import eu.karcags.ceg.utils.getStringProperty
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.launch
import java.nio.file.Paths
import java.time.Duration

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        webSocket("/ws/language-server/kotlin") {
            val languageServerPath = getStringProperty(environment?.config, "language-server.path", "language-server/bin/kotlin-language-server")
            val languageServer = KotlinLanguageServer(
                Paths.get(getLocalDirectory(), languageServerPath),
                call.application.environment.log
            )
            val serverOutput = languageServer.start()

            try {
                launch(Dispatchers.IO) {
                    for (line in serverOutput.lines()) {
                        call.application.environment.log.info(line)
                        validateResponseLine(line)?.let {
                            outgoing.send(Frame.Text(it))
                            languageServer.addResponse(it)
                            languageServer.handleMessages()
                        }
                    }
                }

                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            languageServer.sendMessage(frame.readText())
                        }

                        else -> {}
                    }
                }
            } catch (e: ClosedReceiveChannelException) {
                languageServer.dispose()
            }

            languageServer.dispose()
        }
    }
}