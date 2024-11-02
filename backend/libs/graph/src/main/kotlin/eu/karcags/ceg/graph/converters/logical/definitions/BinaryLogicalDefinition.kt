package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import kotlinx.serialization.Serializable

@Serializable
sealed interface BinaryLogicalDefinition : LogicalDefinition {
    val definitions: Set<LogicalDefinition>

    override fun isSimple(): Boolean = false

    fun childrenAreSimple(): Boolean {
        return definitions.all { it.isSimple() }
    }

    override fun expressions(): List<LogicalExpression> = definitions.map { it.expressions() }.flatten()
}