package eu.karcags.ceg.utils

import eu.karcags.ceg.domain.RequestResult
import io.ktor.http.*
import io.ktor.server.config.*
import java.nio.file.Paths

/**
 * Wraps the object into a success request result.
 * @param status the request status
 * @param T the type of the wrapped object
 * @return success request result
 */
fun <T> T.wrapping(status: HttpStatusCode = HttpStatusCode.OK): RequestResult.Success<T> {
    return RequestResult.Success(this, status.value)
}

/**
 * Gets the local directory.
 * @return the absolute path of the local directory
 */
fun getLocalDirectory(): String {
    return Paths.get("").toAbsolutePath().toString()
}

/**
 * Gets string application property.
 * @param config the application config
 * @param key the key of the property
 * @param orElse the return value in case of missing setting
 * @return the found property or the [orElse] value
 */
fun getStringProperty(config: ApplicationConfig?, key: String, orElse: String): String {
    return config?.property(key)?.getString() ?: orElse
}

/**
 * Gets int application property.
 * @param config the application config
 * @param key the key of the property
 * @param orElse the return value in case of missing setting
 * @return the found property or the [orElse] value
 */
fun getIntProperty(config: ApplicationConfig?, key: String, orElse: Int): Int {
    return getStringProperty(config, key, orElse.toString()).toInt()
}

/**
 * Gets boolean application property.
 * @param config the application config
 * @param key the key of the property
 * @param orElse the return value in case of missing setting
 * @return the found property or the [orElse] value
 */
fun getBooleanProperty(config: ApplicationConfig?, key: String, orElse: Boolean): Boolean {
    return getStringProperty(config, key, orElse.toString()).toBoolean()
}

/**
 * Gets string list application property.
 * @param config the application config
 * @param key the key of the property
 * @return the found property or [emptyList]
 */
fun getStringListProperty(config: ApplicationConfig?, key: String): List<String> {
    return config?.property(key)?.getList() ?: emptyList()
}