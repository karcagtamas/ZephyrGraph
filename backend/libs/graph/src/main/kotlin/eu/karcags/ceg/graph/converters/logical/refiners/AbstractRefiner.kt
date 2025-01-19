package eu.karcags.ceg.graph.converters.logical.refiners

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition

/**
 * Abstract logical refiner.
 * @property key the key of the refiner
 * @constructor creates a refiner
 * @param key the key of the refiner
 */
abstract class AbstractRefiner(val key: String) {

    /**
     * Refines the given logical [graph] into another by the definition.
     * @param graph the source graph
     * @return the refined logical graph
     */
    fun refine(graph: LogicalGraph): LogicalGraph {
        return LogicalGraph(graph.definitions.map { LogicalGraphDefinition(it.effect, refine(it.cause)) })
    }

    /**
     * Refines the given logical definition into another by the definition.
     * @param definition the source definition
     * @return the refined logical definition
     */
    protected abstract fun refine(definition: LogicalDefinition): LogicalDefinition

    /**
     * Collects nodes from the logical definition structure.
     * @param definition the source definition
     * @return the set of the found node definitions
     */
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