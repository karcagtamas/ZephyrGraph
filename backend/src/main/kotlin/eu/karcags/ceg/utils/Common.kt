package eu.karcags.ceg.utils

import eu.karcags.ceg.domain.RequestResult
import io.ktor.http.*
import java.nio.file.Paths

fun <T> T.wrapping(status: HttpStatusCode = HttpStatusCode.OK): RequestResult.Success<T> {
    return RequestResult.Success(this, status.value)
}

fun getLocalDirectory(): String {
    return Paths.get("").toAbsolutePath().toString()
}