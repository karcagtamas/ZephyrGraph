package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graph.converters.logical.exceptions.LogicalEvalException
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graph.converters.logical.resources.DefaultSignResource
import eu.karcags.ceg.graph.converters.logical.resources.Sign
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import kotlinx.serialization.Serializable

/**
 * Node logical definition.
 * @property id the id of the node
 * @property displayName the display name of the node
 * @property expression the expression of the node (optional)
 * @constructor creates a node definition
 * @param id the id of the node
 * @param displayName the display name of the node
 * @param expression the expression of the node (optional)
 */
@Serializable
data class NodeDefinition(val id: String, val displayName: String, private val expression: LogicalExpression?) : LogicalDefinition {

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

    override fun isSimple(): Boolean = true

    override fun expressions(): List<LogicalExpression> = expression?.let { listOf(it) } ?: emptyList()

    /**
     * Gets the NOT wrapped variant of the definition.
     * @return the original node definition wrapped into [NotDefinition]
     */
    fun not(): NotDefinition = NotDefinition(this)
}