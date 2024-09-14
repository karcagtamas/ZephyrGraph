package eu.karcags.ceg.graph.converters.logical.definitions

import kotlinx.serialization.Serializable

@Serializable
sealed interface UnaryLogicalDefinition : SignedLogicalDefinition {
    val definition: LogicalDefinition
}