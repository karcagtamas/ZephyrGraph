package eu.karcags.ceg.graph.converters.logical.definitions

import kotlinx.serialization.Serializable

@Serializable
class OrDefinition(override val left: LogicalDefinition, override val right: LogicalDefinition, override val sign: String) : BinaryLogicalDefinition {
    override fun toString(): String {
        return "($left) $sign ($right)"
    }

    override fun eval(ctx: Map<LogicalDefinition, Boolean>): Boolean {
        return getOrElse(ctx, this) {
            left.eval(ctx) or right.eval(ctx)
        }
    }
}