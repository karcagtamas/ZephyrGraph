package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.*

/**
 * Negation inward mover refiner. It moves all the negation inside the logical wrapping definitions until the nodes.
 */
class NegationInwardMover : AbstractRefiner("negation-inward-mover") {

    override fun refine(definition: LogicalDefinition): LogicalDefinition {
        return when (definition) {
            is NotDefinition -> handleNotDefinition(definition)
            is AndDefinition -> AndDefinition(definition.definitions.map { refine(it) }.toSet())
            is OrDefinition -> OrDefinition(definition.definitions.map { refine(it) }.toSet())
            else -> definition
        }
    }

    private fun handleNotDefinition(definition: NotDefinition): LogicalDefinition {
        return when (definition.inner) {
            is NotDefinition -> refine(definition.inner.inner)
            is AndDefinition -> OrDefinition(
                definition.inner.definitions.map { refine(NotDefinition(it)) }.toSet()
            )

            is OrDefinition -> AndDefinition(
                definition.inner.definitions.map { refine(NotDefinition(it)) }.toSet()
            )

            else -> definition
        }
    }
}