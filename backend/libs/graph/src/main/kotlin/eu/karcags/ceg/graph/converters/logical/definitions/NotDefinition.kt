package eu.karcags.ceg.graph.converters.logical.definitions

import kotlinx.serialization.Serializable

@Serializable
class NotDefinition(override val definition: LogicalDefinition, override val sign: String) : UnaryLogicalDefinition {

    override fun toString(): String {
        return "$sign ($definition)"
    }
}