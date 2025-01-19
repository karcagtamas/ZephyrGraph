package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.BinaryLogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import eu.karcags.ceg.graph.exceptions.GraphConvertException

/**
 * Opposition eliminator refiner. It eliminates all the oppositions in the binary definitions.
 */
class OppositionEliminator : AbstractRefiner("opposition-eliminator") {

    override fun refine(definition: LogicalDefinition): LogicalDefinition {
        return when (definition) {
            is AndDefinition -> AndDefinition(definition.definitions.map { refine(it) }.toSet())
            is OrDefinition -> handleBinaryDefinitions(definition.definitions) { OrDefinition(it) }
            is NotDefinition -> NotDefinition(refine(definition.inner))
            else -> definition
        }
    }

    private fun <T : BinaryLogicalDefinition> handleBinaryDefinitions(
        definitions: Set<LogicalDefinition>,
        constructor: (Set<LogicalDefinition>) -> T
    ): LogicalDefinition {
        var definitions = definitions
            .map { refine(it) }
            .toSet()

        definitions
            .filter { NotDefinition(it) in definitions }
            .forEach {
                definitions = definitions.minus(it)
                definitions = definitions.minus(NotDefinition(it))
            }

        if (definitions.isEmpty()) {
            throw GraphConvertException("No remaining entry for the And definition")
        }

        return if (definitions.size == 1) definitions.first() else constructor(definitions)
    }
}