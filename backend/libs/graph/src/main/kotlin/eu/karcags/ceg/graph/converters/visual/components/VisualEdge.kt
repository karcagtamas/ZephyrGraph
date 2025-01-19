package eu.karcags.ceg.graph.converters.visual.components

import kotlinx.serialization.Serializable

/**
 * Represents an edge in the visual graph.
 * @property source the source node of the edge
 * @property target the target node of the edge
 * @property id the id of the edge
 * @constructor creates a visual edge
 * @param source the source node of the edge
 * @param target the target node of the edge
 */
@Serializable
data class VisualEdge(val source: VisualNode, val target: VisualNode) {
    var id: String = "${source.id}-${target.id}"
}