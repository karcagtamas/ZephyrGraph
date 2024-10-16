package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.BinaryLogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import eu.karcags.ceg.graph.exceptions.GraphConvertException

class PreOptimizer : AbstractRefiner("pre-optimizer") {

    override fun refine(definition: LogicalDefinition): LogicalDefinition {
        return when (definition) {
            is AndDefinition -> handleBinaryDefinition(definition)
            is OrDefinition -> handleBinaryDefinition(definition)
            is NotDefinition -> NotDefinition(refine(definition.inner))
            else -> definition
        }
    }

    private fun <T : BinaryLogicalDefinition> handleBinaryDefinition(definition: T): BinaryLogicalDefinition {
        return when (definition) {
            is AndDefinition -> AndDefinition(collapse(definition.definitions) { it is AndDefinition })

            is OrDefinition -> OrDefinition(collapse(definition.definitions) { it is OrDefinition })

            else -> throw GraphConvertException("Unhandled binary definition")
        }
    }

    private fun collapse(
        definitions: Set<LogicalDefinition>,
        predicate: (LogicalDefinition) -> Boolean
    ): Set<LogicalDefinition> {
        return definitions.map {
            if (predicate(it)) {
                when (it) {
                    is BinaryLogicalDefinition -> it.definitions.map { refine(it) }.toSet()
                    else -> throw GraphConvertException("Invalid predicate for binary collapser")
                }
            } else {
                setOf(refine(it))
            }
        }.flatten().toSet()
    }
}