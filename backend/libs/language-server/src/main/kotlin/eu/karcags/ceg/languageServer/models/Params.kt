package eu.karcags.ceg.languageServer.models

import kotlinx.serialization.Serializable

@Serializable
data class Params(
    val locale: String,
    var processId: Int?,
    val rootPath: String,
    val rootUri: String,
    val trace: String,
)