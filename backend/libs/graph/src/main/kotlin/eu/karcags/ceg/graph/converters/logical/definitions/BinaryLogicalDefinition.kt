package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import kotlinx.serialization.Serializable

/**
 * Binary logical definition.
 * @property definitions the wrapped logical definitions
 */
@Serializable
sealed interface BinaryLogicalDefinition : LogicalDefinition {
    val definitions: Set<LogicalDefinition>

    override fun isSimple(): Boolean = false

    /**
     * Determines all the children are simple or not.
     * @return true if all the wrapped children in [definitions] are simple.
     */
    fun childrenAreSimple(): Boolean {
        return definitions.all { it.isSimple() }
    }

    override fun expressions(): List<LogicalExpression> = definitions.map { it.expressions() }.flatten()
}