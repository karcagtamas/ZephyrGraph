package eu.karcags.ceg.utils

import eu.karcags.ceg.domain.RequestResult
import io.ktor.http.*
import io.ktor.server.config.*
import java.nio.file.Paths

fun <T> T.wrapping(status: HttpStatusCode = HttpStatusCode.OK): RequestResult.Success<T> {
    return RequestResult.Success(this, status.value)
}

fun getLocalDirectory(): String {
    return Paths.get("").toAbsolutePath().toString()
}

fun getStringProperty(config: ApplicationConfig?, key: String, orElse: String): String {
    return config?.property(key)?.getString() ?: orElse
}

fun getIntProperty(config: ApplicationConfig?, key: String, orElse: Int): Int {
    return getStringProperty(config, key, orElse.toString()).toInt()
}

fun getStringListProperty(config: ApplicationConfig?, key: String): List<String> {
    return config?.property(key)?.getList() ?: emptyList()
}