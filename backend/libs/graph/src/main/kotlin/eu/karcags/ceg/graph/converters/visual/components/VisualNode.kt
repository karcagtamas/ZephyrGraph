package eu.karcags.ceg.graph.converters.visual.components

import kotlinx.serialization.Serializable

/**
 * Represents a node in the visual graph.
 * @property id the id of the node
 * @property displayName the display name of the node
 * @property meta the metadata for the specific node definitions
 * @constructor creates a visual node
 * @param id the id of the node
 * @param displayName the display name of the node
 * @param meta the metadata for the specific node definitions
 */
@Serializable
data class VisualNode(val id: String, val displayName: String, val meta: NodeMeta)