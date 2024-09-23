package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition

abstract class AbstractRefiner() {

    fun refine(graph: LogicalGraph): LogicalGraph {
        return LogicalGraph(graph.definitions.map { refine(it) })
    }

    protected abstract fun refine(definition: LogicalDefinition): LogicalDefinition
}