package eu.karcags.models

import kotlinx.serialization.Serializable

@Serializable
data class Edge(val source: Node, val target: Node)
