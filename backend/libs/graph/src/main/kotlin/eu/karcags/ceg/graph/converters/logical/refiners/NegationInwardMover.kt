package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.*
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource

class NegationInwardMover(resource: AbstractSignResource) : AbstractRefiner(resource) {

    override fun refine(definition: LogicalDefinition): LogicalDefinition {
        return when (definition) {
            is NotDefinition -> handleNotDefinition(definition)
            is AndDefinition -> AndDefinition(refine(definition.left), refine(definition.right), resource.AND)
            is OrDefinition -> OrDefinition(refine(definition.left), refine(definition.right), resource.OR)
            else -> definition
        }
    }

    private fun handleNotDefinition(definition: NotDefinition): LogicalDefinition {
        return when (definition.definition) {
            is NotDefinition -> refine(definition.definition.definition)
            is AndDefinition -> OrDefinition(refine(NotDefinition(definition.definition.left, resource.NOT)), refine(NotDefinition(definition.definition.right, resource.NOT)), resource.OR)
            is OrDefinition -> AndDefinition(refine(NotDefinition(definition.definition.left, resource.NOT)), refine(NotDefinition(definition.definition.right, resource.NOT)), resource.AND)
            else -> definition.definition
        }
    }
}