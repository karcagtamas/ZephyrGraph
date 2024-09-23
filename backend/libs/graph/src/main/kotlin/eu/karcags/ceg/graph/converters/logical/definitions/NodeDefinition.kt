package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graph.converters.logical.exceptions.LogicalEvalException
import kotlinx.serialization.Serializable

@Serializable
class NodeDefinition(val id: String, val displayName: String) : LogicalDefinition {

    override fun toString(): String {
        return displayName
    }

    override fun eval(ctx: Map<LogicalDefinition, Boolean>): Boolean {
        if (!ctx.containsKey(this)) {
            throw LogicalEvalException("Node eval value not found in the context")
        }

        return ctx.getValue(this)
    }
}