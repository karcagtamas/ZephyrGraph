package eu.karcags.`language-server`

import eu.karcags.`language-server`.models.Message
import io.ktor.util.logging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.*
import java.nio.file.Path
import java.nio.file.Paths
import java.util.LinkedList
import kotlin.io.path.pathString

class KotlinLanguageServer(private val executablePath: Path, private val logger: Logger) {
    companion object {
        const val INITIALIZER_METHOD = "initialize"
        var serverProcessId = 0
    }

    private val name = "KotlinLanguageServer"

    @Volatile
    private var serverConnection: Process? = null

    private val subscribers = hashMapOf<String, suspend (String) -> Unit>()
    private val responses = LinkedList<String>()

    private val messages = LinkedList<String>()

    suspend fun start() {
        serverConnection = createServerProcess(executablePath, listOf())
        val inputReader = BufferedReader(InputStreamReader(serverConnection!!.inputStream))

        while (serverConnection!!.isAlive) {
            synchronized(messages) {
                logger.info("HANDLING")
                if (responses.size > 0 && messages.size > 0) {
                    handleNextMessage()
                }
            }

            withContext(Dispatchers.IO) {
                inputReader.readLine()
            }?.let {
                logger.info("Server message response:\n$it")

                handleValidResponse(it) { valid ->
                    responses.add(valid)
                    subscribers.forEach { (key, fn) ->
                        logger.info("$key subscriber called back with value: $valid")
                        fn(valid)
                    }
                }
            }
        }
    }

    fun sendMessage(messageString: String) {
        synchronized(messages) {
            messages.push(messageString)
            logger.info("Server message received:\n$messageString")
        }
    }

    fun subscribe(key: String, subscriber: suspend (String) -> Unit) {
        subscribers[key] = subscriber
    }

    fun unsubscribe(key: String) {
        subscribers.remove(key)
    }

    fun dispose() {
        serverConnection?.destroy()
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
                }
        } catch (e: IOException) {
            logger.error("Failed to start $name: ${e.message}")
            logger.error(e)
            null
        }
    }

    private suspend fun handleValidResponse(response: String, handler: suspend (String) -> Unit) {
        if (response.isBlank() || response.isEmpty()) {
            return
        }

        val regex = "(\\{[{}\\\":\\w\\[\\],. ()/]*\\})(Content-Length: [0-9]*)?".toRegex()
        var result = ""
        regex.find(response)?.let {
            val (json) = it.destructured
            result = json
        }


        try {
            Json.parseToJsonElement(result)
            handler(result)
        } catch (e: Exception) {
            return
        }
    }

    private fun handleNextMessage() {
        val message = Message.decode(messages.pop()).apply {
            if (method == INITIALIZER_METHOD) {
                serverProcessId = ProcessHandle.current().pid().toInt()
                this.processId = serverProcessId
            }
        }

        val printWriter = PrintWriter(OutputStreamWriter(serverConnection!!.outputStream))

        val encodedMessage = Message.encode(message);
        printWriter.print("Content-Length: ${encodedMessage.length}\n\n$encodedMessage")
        printWriter.flush()
        logger.info("Message send forward the the language server: $encodedMessage")
    }
}