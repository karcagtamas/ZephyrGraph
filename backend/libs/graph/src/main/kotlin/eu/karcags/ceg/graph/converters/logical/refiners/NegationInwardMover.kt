package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.*

class NegationInwardMover() : AbstractRefiner() {

    override fun refine(definition: LogicalDefinition): LogicalDefinition {
        return when (definition) {
            is NotDefinition -> handleNotDefinition(definition)
            is AndDefinition -> AndDefinition(refine(definition.left), refine(definition.right))
            is OrDefinition -> OrDefinition(refine(definition.left), refine(definition.right))
            else -> definition
        }
    }

    private fun handleNotDefinition(definition: NotDefinition): LogicalDefinition {
        return when (definition.inner) {
            is NotDefinition -> refine(definition.inner.inner)
            is AndDefinition -> OrDefinition(refine(NotDefinition(definition.inner.left)), refine(NotDefinition(definition.inner.right)))
            is OrDefinition -> AndDefinition(refine(NotDefinition(definition.inner.left)), refine(NotDefinition(definition.inner.right)))
            else -> definition
        }
    }
}