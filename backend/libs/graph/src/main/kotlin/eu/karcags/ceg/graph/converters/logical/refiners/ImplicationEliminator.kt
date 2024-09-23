package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.*

class ImplicationEliminator() : AbstractRefiner() {

    override fun refine(definition: LogicalDefinition): LogicalDefinition {
        return when (definition) {
            is ImplicateDefinition -> OrDefinition(NotDefinition(refine(definition.left)), refine(definition.right))
            is NotDefinition -> NotDefinition(refine(definition.inner))
            is OrDefinition -> OrDefinition(refine(definition.left), refine(definition.right))
            is AndDefinition -> AndDefinition(refine(definition.left), refine(definition.right))
            else -> definition
        }
    }
}