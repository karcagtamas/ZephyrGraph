package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import kotlinx.serialization.Serializable

/**
 * Represents a logical definition in the logical graph.
 * @property effect the connected effect node definition
 * @property cause the connected logical node definition
 * @constructor creates a logical graph definition
 * @param effect the connected effect node definition
 * @param cause the connected logical node definition
 */
@Serializable
data class LogicalGraphDefinition(val effect: NodeDefinition, val cause: LogicalDefinition)
