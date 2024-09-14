package eu.karcags.ceg.graph.converters.logical.definitions

import kotlinx.serialization.Serializable

@Serializable
class AndDefinition(override val left: LogicalDefinition, override val right: LogicalDefinition, override val sign: String) : BinaryLogicalDefinition {
    override fun toString(): String {
        return "($left) $sign ($right)"
    }
}