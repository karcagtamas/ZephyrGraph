package eu.karcags.ceg.graph.converters.visual

import eu.karcags.ceg.graph.converters.visual.components.VisualEdge
import eu.karcags.ceg.graph.converters.visual.components.VisualNode
import kotlinx.serialization.Serializable

/**
 * Represent a visual graph with all the nodes and edges.
 * @property edges the directed edges of the visual graph
 * @property nodes the nodes of the visual graph
 * @constructor creates a visual graph
 * @param edges the directed edges of the visual graph
 * @param nodes the nodes of the visual graph
 */
@Serializable
data class VisualGraph(val edges: List<VisualEdge>, val nodes: List<VisualNode>)