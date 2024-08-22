package eu.karcags.ceg.domain

import kotlinx.serialization.Serializable

@Serializable
data class ErrorData(val cause: String?, val stackTrace: List<String>) {

    constructor(throwable: Throwable) : this(throwable.message, throwable.stackTrace.toList().map { it.toString() })
}