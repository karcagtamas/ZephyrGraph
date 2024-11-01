package eu.karcags.ceg.graph.converters.logical.definitions

import kotlinx.serialization.Serializable

@Serializable
sealed interface UnaryLogicalDefinition : LogicalDefinition {
    val inner: LogicalDefinition

    override fun isSimple(): Boolean = inner.isSimple()

    override fun expressions(): List<NodeExpression> = inner.expressions() // TODO: Get the opposites

}