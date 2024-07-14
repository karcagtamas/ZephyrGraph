package eu.karcags.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Edge(val source: Node, val target: Node) {
    var id: String = "${source.id}-${target.id}"
}
