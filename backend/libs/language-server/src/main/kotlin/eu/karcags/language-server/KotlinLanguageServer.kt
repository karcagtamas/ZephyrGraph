package eu.karcags.`language-server`

import io.ktor.util.logging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.io.*
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import kotlin.io.path.pathString
import eu.karcags.`language-server`.models.Message

class KotlinLanguageServer(private val executablePath: Path, private val logger: Logger) {
    companion object {
        const val initializerMethod = "initialize"
        var processId = 0
    }

    private val name = "KotlinLanguageServer"

    @Volatile
    private var serverConnection: Process? = null

    fun start() {
        serverConnection = createServerProcess(executablePath, listOf())
    }

    fun handle(socket: IWebSocket) {
        if (serverConnection != null) {
            forward(socket, serverConnection!!) { message ->
                if (Message.isRequest(message)) {
                    logger.info("Server message received:\n$message")

                    if (message.method == initializerMethod) {
                        processId = ProcessHandle.current().pid().toInt()
                        message.processId = processId
                    }
                }

                if (Message.isResponse(message)) {
                    logger.info("Server message sent:\n$message")
                }

                message
            }
        }
    }

    private fun createServerProcess(executable: Path, args: List<String>): Process? {
        return try {
            val command = mutableListOf("cmd", "/c", executable.pathString).apply { addAll(args) }

            ProcessBuilder(command)
                .directory(File(Paths.get("").toAbsolutePath().toString()))
                .redirectErrorStream(true)
                .start()
                .also {
                    logger.info("$name started with PID: ${it.pid()}")
                    it.waitFor(5000, TimeUnit.MILLISECONDS)
                }
        } catch (e: IOException) {
            logger.error("Failed to start $name: ${e.message}")
            logger.error(e)
            null
        }
    }

    private fun forward(socket: IWebSocket, serverConnection: Process, onMessage: (Message) -> Message) {
        val job = CoroutineScope(Dispatchers.IO).launch {
            val inputReader = BufferedReader(InputStreamReader(serverConnection.inputStream))
            val outputWriter = PrintWriter(OutputStreamWriter(serverConnection.outputStream))

            val webSocketToProcess = launch(Dispatchers.IO) {
                try {
                    socket.onMessage { messageString ->
                        val message = Message.decode(messageString)
                        val processedMessage = onMessage(message)
                        outputWriter.println(Message.encode(processedMessage))
                    }
                } catch (e: Throwable) {
                    logger.error(e)
                }
            }

            val processToWebSocket = launch(Dispatchers.IO) {
                try {
                    var line: String? = null

                    while (serverConnection.isAlive && inputReader.readLine().also { line = it } != null) {
                        line?.let {
                            socket.send(it)
                        }
                    }
                } catch (e: Throwable) {
                    logger.error(e)
                } finally {
                    socket.dispose()
                }
            }

            joinAll(webSocketToProcess, processToWebSocket)
        }

        //job.invokeOnCompletion {
        //    if (serverConnection.isAlive) {
        //        serverConnection.destroy()
        //    }
        //}
    }
}