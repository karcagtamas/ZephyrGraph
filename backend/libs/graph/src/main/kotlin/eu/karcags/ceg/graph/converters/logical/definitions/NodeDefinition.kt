package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graph.converters.logical.exceptions.LogicalEvalException
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graph.converters.logical.resources.DefaultSignResource
import eu.karcags.ceg.graph.converters.logical.resources.Sign
import kotlinx.serialization.Serializable

@Serializable
class NodeDefinition(val id: String, val displayName: String) : LogicalDefinition {

    override fun toString(): String {
        return stringify(DefaultSignResource())
    }

    override fun eval(ctx: Map<LogicalDefinition, Boolean>): Boolean {
        if (!ctx.containsKey(this)) {
            throw LogicalEvalException("Node eval value not found in the context")
        }

        return ctx.getValue(this)
    }

    override fun stringify(resource: AbstractSignResource): String {
        return resource.get(Sign.Node, displayName, id)
    }
}