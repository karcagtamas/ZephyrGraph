package eu.karcags.`language-server`.models

import kotlinx.serialization.Serializable

@Serializable
data class Params(
    val locale: String,
    var processId: Int?,
    val rootPath: String,
    val rootUri: String,
    val trace: String,
)