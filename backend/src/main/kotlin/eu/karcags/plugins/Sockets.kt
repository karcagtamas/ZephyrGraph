package eu.karcags.plugins

import eu.karcags.`language-server`.IWebSocket
import eu.karcags.`language-server`.KotlinLanguageServer
import eu.karcags.utils.getLocalDirectory
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
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

    val languageServer = KotlinLanguageServer(
        Paths.get(getLocalDirectory(), "language-server", "bin", "kotlin-language-server"),
        environment.log
    ).also { it.start() }

    routing {
        webSocket("/ws/language-server/kotlin") {
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    println(frame.readText())
                }
            }

            for (frame in incoming) {
                if (frame is Frame.Text) {
                    println(frame.readText())
                }
            }

            val socket = object : IWebSocket {
                override suspend fun send(content: String) {
                    outgoing.send(Frame.Text(content))
                }

                override fun onMessage(cb: (String) -> Unit) {
                    launch(Dispatchers.IO) {
                        for (frame in incoming) {
                            if (frame is Frame.Text) {
                                cb(frame.readText())
                            }
                        }
                    }
                }

                override fun onError(cb: (Throwable) -> Unit) {
                    launch(Dispatchers.IO) {
                        try {
                            for (frame in incoming) {

                            }
                        } catch (e: Throwable) {
                            cb(e)
                        }
                    }
                }

                override suspend fun onClose(cb: () -> Unit) {
                    close()
                    cb()
                }

                override suspend fun dispose() {
                    close()
                }
            }

            languageServer.handle(socket)
        }
    }
}