package eu.karcags.ceg.graph.converters.visual.components

import kotlinx.serialization.Serializable

@Serializable
data class VisualNode(val id: String, val displayName: String, val meta: NodeMeta)