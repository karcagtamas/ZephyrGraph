package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graph.converters.logical.resources.DefaultSignResource
import eu.karcags.ceg.graph.converters.logical.resources.Sign
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import kotlinx.serialization.Serializable

@Serializable
data class NotDefinition(override val inner: LogicalDefinition) : UnaryLogicalDefinition {

    override fun toString(): String {
        return stringify(DefaultSignResource())
    }

    override fun eval(ctx: Map<LogicalDefinition, Boolean>): Boolean {
        return getOrElse(ctx, this) {
            !inner.eval(ctx)
        }
    }

    override fun stringify(resource: AbstractSignResource): String {
        return resource.get(Sign.Not, inner.stringify(resource))
    }

    override fun expressions(): List<LogicalExpression> = inner.expressions().map { it.invert() }
}