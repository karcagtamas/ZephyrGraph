package eu.karcags.ceg.graph.converters.logical.definitions

import kotlinx.serialization.Serializable

@Serializable
sealed interface BinaryLogicalDefinition : LogicalDefinition {
    val left: LogicalDefinition
    val right: LogicalDefinition
}