package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.*
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource

class ImplicationElimination(resource: AbstractSignResource) : AbstractRefiner(resource) {

    override fun refine(definition: LogicalDefinition): LogicalDefinition {
        return when (definition) {
            is ImplicateDefinition -> OrDefinition(NotDefinition(refine(definition.left), resource.NOT), refine(definition.right), resource.OR)
            is NotDefinition -> NotDefinition(refine(definition.definition), resource.NOT)
            is OrDefinition -> OrDefinition(refine(definition.left), refine(definition.right), resource.OR)
            is AndDefinition -> AndDefinition(refine(definition.left), refine(definition.right), resource.AND)
            else -> definition
        }
    }
}