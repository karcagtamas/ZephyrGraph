package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import kotlinx.serialization.Serializable

/**
 * Unary logical definition.
 * @property inner the wrapped logical definition
 */
@Serializable
sealed interface UnaryLogicalDefinition : LogicalDefinition {
    val inner: LogicalDefinition

    override fun isSimple(): Boolean = inner.isSimple()

    override fun expressions(): List<LogicalExpression> = inner.expressions()
}