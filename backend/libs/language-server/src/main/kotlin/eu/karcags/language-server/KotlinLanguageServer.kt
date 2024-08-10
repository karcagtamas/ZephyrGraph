package eu.karcags.`language-server`

import io.ktor.util.logging.*
import java.io.*
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import kotlin.io.path.pathString
import eu.karcags.`language-server`.models.Message
import kotlinx.coroutines.*

class KotlinLanguageServer(private val executablePath: Path, private val logger: Logger) {
    companion object {
        const val INITIALIZER_METHOD = "initialize"
        var serverProcessId = 0
    }

    private val name = "KotlinLanguageServer"

    @Volatile
    private var serverConnection: Process? = null

    fun start() {
        serverConnection = createServerProcess(executablePath, listOf())
    }

    fun sendMessage(messageString: String): String {
        val message = Message.decode(messageString).apply {
            if (method == INITIALIZER_METHOD) {
                serverProcessId = ProcessHandle.current().pid().toInt()
                this.processId = serverProcessId
            }
        }

        logger.info("Server message received:\n$message")
        val inputReader = BufferedReader(InputStreamReader(serverConnection!!.inputStream))
        val printWriter = PrintWriter(OutputStreamWriter(serverConnection!!.outputStream))

        val encodedMessage = Message.encode(message);
        printWriter.print("Content-Length: ${encodedMessage.length}\n\n$encodedMessage")
        printWriter.flush()

        var line: String? = null

        while (serverConnection!!.isAlive && inputReader.readLine().also { line = it } != null) {
            line?.let {
                logger.info("Server message response:\n$it")
                //return it
            }
        }

        return line ?: ""
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
}