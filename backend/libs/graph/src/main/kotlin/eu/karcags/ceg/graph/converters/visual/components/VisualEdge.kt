package eu.karcags.ceg.graph.converters.visual.components

import kotlinx.serialization.Serializable

@Serializable
data class VisualEdge(val source: VisualNode, val target: VisualNode) {
    var id: String = "${source.id}-${target.id}"
}