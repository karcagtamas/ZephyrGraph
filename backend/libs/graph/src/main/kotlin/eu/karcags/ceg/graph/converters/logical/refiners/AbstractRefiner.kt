package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition

abstract class AbstractRefiner(val key: String) {

    fun refine(graph: LogicalGraph): LogicalGraph {
        return LogicalGraph(graph.definitions.map { LogicalGraphDefinition(it.effect, refine(it.cause)) })
    }

    protected abstract fun refine(definition: LogicalDefinition): LogicalDefinition

    protected fun collectNodes(definition: LogicalDefinition): Set<NodeDefinition> {
        return when (definition) {
            is NodeDefinition -> setOf(definition)
            is NotDefinition -> collectNodes(definition.inner)
            is OrDefinition -> definition.definitions.map { collectNodes(it) }.flatten().toSet()
            is AndDefinition -> definition.definitions.map { collectNodes(it) }.flatten().toSet()
            else -> emptySet()
        }
    }
}