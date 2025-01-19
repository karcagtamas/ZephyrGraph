package eu.karcags.ceg.languageServer

import eu.karcags.ceg.common.exceptions.ServerException
import eu.karcags.ceg.common.utils.os.OS
import eu.karcags.ceg.common.utils.os.OSDetector
import eu.karcags.ceg.languageServer.models.Message
import io.ktor.util.logging.*
import kotlinx.serialization.json.Json
import java.io.*
import java.nio.file.Paths
import java.util.*

/**
 * Kotlin Language Server for external process managing.
 *
 * @property executablePath the path of the external server executable file
 * @property executableName the name of the external server executable file
 * @property logger server logger
 * @constructor creates a Language Server instance.
 * @param executablePath the path of the external server executable file
 * @param executableName the name of the external server executable file
 * @param logger server logger
 */
class KotlinLanguageServer(private val executablePath: String, private val executableName: String, private val logger: Logger) {
    companion object {
        const val INITIALIZER_METHOD = "initialize"
        var serverProcessId = 0
    }

    private val name = "KotlinLanguageServer"

    @Volatile
    private var serverConnection: Process? = null
    private val responses = LinkedList<String>()
    private val messages = LinkedList<String>()

    /**
     * Initializes external server process and connects to its output stream.
     * @return The stream reader of the server process.
     */
    fun start(): BufferedReader {
        serverConnection = createServerProcess(listOf())
        val inputReader = BufferedReader(InputStreamReader(serverConnection!!.inputStream))

        return inputReader
    }

    /**
     * Stores external server responses for later processing.
     * @param response the response line
     */
    fun addResponse(response: String) {
        responses.add(response)
    }

    /**
     * Stores message for later sending to the external server.
     * @param messageString the message string
     */
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

    /**
     * Process all the available messages.
     */
    fun handleMessages() {
        if (responses.size <= 0) {
            return
        }

        while (messages.size > 0) {
            handleNextMessage()
        }
    }

    /**
     * Disposes the language server, the external server process and its child processes.
     */
    fun dispose() {
        serverConnection?.children()?.forEach { child ->
            child.destroy()
            logger.info("$name sub-process stopped with PID: ${child.pid()}")
        }
        serverConnection?.destroy()
        logger.info("$name stopped with PID: ${serverConnection?.pid()}")
    }

    private fun createServerProcess(args: List<String>): Process? {
        return try {
            val command = getCommand(executablePath, executableName, args)

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

    private fun getCommand(executablePath: String, executableName: String, args: List<String>): List<String> {
        val os = OSDetector().determineOS()

        return when (os) {
            OS.Windows -> mutableListOf("cmd", "/c", "$executablePath/$executableName.bat")
            OS.Linux -> mutableListOf("$executablePath/$executableName")
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

        val encodedMessage = Message.encode(message)
        printWriter.print("Content-Length: ${encodedMessage.length}\n\n$encodedMessage")
        printWriter.flush()
        logger.info("Message send forward the the language server: $encodedMessage")
    }
}

/**
 * Validates response line. Checks content length prefix and valid JSON content.
 * @param line the line to be validated
 * @return the processed JSON content as string or null
 */
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