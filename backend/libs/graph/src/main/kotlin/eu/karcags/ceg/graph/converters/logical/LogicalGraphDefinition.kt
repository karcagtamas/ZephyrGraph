package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import kotlinx.serialization.Serializable

@Serializable
data class LogicalGraphDefinition(val effect: NodeDefinition, val cause: LogicalDefinition)
