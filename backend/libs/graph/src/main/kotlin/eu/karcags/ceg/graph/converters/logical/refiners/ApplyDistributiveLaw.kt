package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource

class ApplyDistributiveLaw(resource: AbstractSignResource) : AbstractRefiner(resource) {

    override fun refine(definition: LogicalDefinition): LogicalDefinition {
        return when (definition) {
            is OrDefinition -> handleOrDefinition(definition)
            is AndDefinition -> AndDefinition(refine(definition.left), refine(definition.right), resource.AND)
            else -> definition
        }
    }

    private fun handleOrDefinition(definition: OrDefinition): LogicalDefinition {
        if (definition.left is AndDefinition) {
            return AndDefinition(refine(OrDefinition(definition.left.left, definition.right, resource.OR)), refine(OrDefinition(definition.left.right, definition.right, resource.OR)), resource.AND)
        }

        if (definition.right is AndDefinition) {
            return AndDefinition(refine(OrDefinition(definition.left, definition.right.left, resource.OR)), refine(OrDefinition(definition.left, definition.right.right, resource.OR)), resource.AND)
        }

        return OrDefinition(refine(definition.left), refine(definition.right), resource.OR)
    }
}