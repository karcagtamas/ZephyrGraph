package eu.karcags.ceg.graph.converters.logical.definitions

import kotlinx.serialization.Serializable

@Serializable
sealed interface UnaryLogicalDefinition : LogicalDefinition {
    val inner: LogicalDefinition
}