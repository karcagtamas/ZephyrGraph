package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource

abstract class AbstractRefiner(protected val resource: AbstractSignResource) {

    fun refine(graph: LogicalGraph): LogicalGraph {
        return LogicalGraph(graph.definitions.map { refine(it) })
    }

    abstract protected fun refine(definition: LogicalDefinition): LogicalDefinition
}