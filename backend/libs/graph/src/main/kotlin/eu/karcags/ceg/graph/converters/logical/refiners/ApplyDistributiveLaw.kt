package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition

class ApplyDistributiveLaw() : AbstractRefiner() {

    override fun refine(definition: LogicalDefinition): LogicalDefinition {
        return when (definition) {
            is OrDefinition -> handleOrDefinition(definition)
            is AndDefinition -> AndDefinition(refine(definition.left), refine(definition.right))
            else -> definition
        }
    }

    private fun handleOrDefinition(definition: OrDefinition): LogicalDefinition {
        if (definition.left is AndDefinition) {
            return AndDefinition(
                refine(OrDefinition(definition.left.left, definition.right)),
                refine(OrDefinition(definition.left.right, definition.right)),
            )
        }

        if (definition.right is AndDefinition) {
            return AndDefinition(
                refine(OrDefinition(definition.left, definition.right.left)),
                refine(OrDefinition(definition.left, definition.right.right)),
            )
        }

        return OrDefinition(refine(definition.left), refine(definition.right),)
    }
}