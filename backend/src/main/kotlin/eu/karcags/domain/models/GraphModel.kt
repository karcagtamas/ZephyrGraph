package eu.karcags.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class GraphModel(val nodes: List<Node>, val edges: List<Edge>)
