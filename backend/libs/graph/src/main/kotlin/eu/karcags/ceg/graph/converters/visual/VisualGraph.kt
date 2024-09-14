package eu.karcags.ceg.graph.converters.visual

import eu.karcags.ceg.graph.converters.visual.components.VisualEdge
import eu.karcags.ceg.graph.converters.visual.components.VisualNode
import kotlinx.serialization.Serializable

@Serializable
data class VisualGraph(val edges: List<VisualEdge>, val nodes: List<VisualNode>)