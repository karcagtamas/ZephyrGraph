package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition

abstract class AbstractRefiner(val key: String) {

    fun refine(graph: LogicalGraph): LogicalGraph {
        return LogicalGraph(graph.definitions.map { Pair(it.first, refine(it.second)) })
    }

    protected abstract fun refine(definition: LogicalDefinition): LogicalDefinition
}