package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graph.converters.logical.resources.DefaultSignResource
import eu.karcags.ceg.graph.converters.logical.resources.Sign
import kotlinx.serialization.Serializable

/**
 * AND logical definition.
 * @property definitions all the wrapped logical definitions
 * @constructor creates an AND definition
 * @param definitions all the wrapped logical definitions
 */
@Serializable
data class AndDefinition(override val definitions: Set<LogicalDefinition>) : BinaryLogicalDefinition {
    override fun toString(): String {
        return stringify(DefaultSignResource())
    }

    override fun eval(ctx: Map<LogicalDefinition, Boolean>): Boolean {
        return getOrElse(ctx, this) {
            definitions.fold(true) { ac, def -> ac and def.eval(ctx) }
        }
    }

    override fun stringify(resource: AbstractSignResource): String {
        return resource.get(Sign.And, *definitions.map { it.stringify(resource) }.toTypedArray())
    }
}