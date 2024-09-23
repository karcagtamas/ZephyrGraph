package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graph.converters.logical.resources.DefaultSignResource
import eu.karcags.ceg.graph.converters.logical.resources.Sign
import kotlinx.serialization.Serializable

@Serializable
class ImplicateDefinition(override val left: LogicalDefinition, override val right: LogicalDefinition) : BinaryLogicalDefinition {
    override fun toString(): String {
        return stringify(DefaultSignResource())
    }

    override fun eval(ctx: Map<LogicalDefinition, Boolean>): Boolean {
        return getOrElse(ctx, this) {
            !left.eval(ctx) or right.eval(ctx)
        }
    }

    override fun stringify(resource: AbstractSignResource): String {
        return resource.get(Sign.Implicate, left.stringify(resource), right.stringify(resource))
    }
}