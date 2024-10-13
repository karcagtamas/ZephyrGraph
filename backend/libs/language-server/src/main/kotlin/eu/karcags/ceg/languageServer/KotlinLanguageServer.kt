package eu.karcags.ceg.languageServer

import eu.karcags.ceg.common.exceptions.ServerException
import eu.karcags.ceg.common.utils.os.OS
import eu.karcags.ceg.common.utils.os.OSDetector
import eu.karcags.ceg.languageServer.models.Message
import io.ktor.util.logging.*
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
    private val responses = LinkedList<String>()
    private val messages = LinkedList<String>()

    fun start(): BufferedReader {
        serverConnection = createServerProcess(executablePath, listOf())
        val inputReader = BufferedReader(InputStreamReader(serverConnection!!.inputStream))

        return inputReader
    }

    fun addResponse(response: String) {
        responses.add(response)
    }

    fun sendMessage(messageString: String) {
        synchronized(messages) {
            messages.push(messageString)
            logger.info("Server message received:\n$messageString")

            logger.info("HANDLING")
            if (responses.size > 0) {
                handleMessages()
            }
        }
    }

    fun handleMessages() {
        if (responses.size <= 0) {
            return
        }

        while (messages.size > 0) {
            handleNextMessage()
        }
    }

    fun dispose() {
        serverConnection?.destroy()
    }

    private fun createServerProcess(executable: Path, args: List<String>): Process? {
        return try {
            val command = getCommand(executable, args)

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

    private fun getCommand(executable: Path, args: List<String>): List<String> {
        val os = OSDetector().determineOS()

        return when (os) {
            OS.Windows -> mutableListOf("cmd", "/c", executable.pathString)
            OS.Linux -> mutableListOf(executable.pathString)
            else -> throw ServerException("Invalid operation system")
        }.apply { addAll(args) }
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

fun validateResponseLine(line: String): String? {
    if (line.isBlank() || line.isEmpty()) {
        return null
    }

    val regex = "(\\{.*})(Content-Length: [0-9]*)?".toRegex()
    var result = ""
    regex.find(line)?.let {
        val (json) = it.destructured
        result = json
    }

    if (result.isEmpty()) {
        return null
    }

    try {
        Json.parseToJsonElement(result)
        return result
    } catch (e: Exception) {
        println(e)
        return null
    }
}