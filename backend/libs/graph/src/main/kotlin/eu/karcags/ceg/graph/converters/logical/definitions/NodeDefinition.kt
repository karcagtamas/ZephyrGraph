package eu.karcags.ceg.graph.converters.logical.definitions

import kotlinx.serialization.Serializable

@Serializable
class NodeDefinition(val id: String, val displayName: String) : LogicalDefinition {

    override fun toString(): String {
        return displayName
    }
}