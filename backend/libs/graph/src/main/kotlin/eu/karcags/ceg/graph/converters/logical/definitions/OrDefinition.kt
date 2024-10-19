package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graph.converters.logical.resources.DefaultSignResource
import eu.karcags.ceg.graph.converters.logical.resources.Sign
import kotlinx.serialization.Serializable

@Serializable
data class OrDefinition(override val definitions: Set<LogicalDefinition>) : BinaryLogicalDefinition {
    override fun toString(): String {
        return stringify(DefaultSignResource())
    }

    override fun eval(ctx: Map<LogicalDefinition, Boolean>): Boolean {
        return getOrElse(ctx, this) {
            definitions.fold(false) { ac, def -> ac or def.eval(ctx) }
        }
    }

    override fun stringify(resource: AbstractSignResource): String {
        return resource.get(Sign.Or, *definitions.map { it.stringify(resource) }.toTypedArray())
    }
}