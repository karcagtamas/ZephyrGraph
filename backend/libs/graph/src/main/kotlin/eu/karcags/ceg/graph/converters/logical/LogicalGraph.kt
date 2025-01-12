package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import eu.karcags.ceg.graph.converters.logical.refiners.AbstractRefiner
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import kotlinx.serialization.Serializable

/**
 * Represents a logical graph.
 * @property definitions the logical definitions of the graph
 * @constructor creates a logical graph from the set of definitions
 * @param definitions the logical definitions of the graph
 */
@Serializable
data class LogicalGraph(val definitions: List<LogicalGraphDefinition>) {

    /**
     * Stringifies the logical graph definitions by the given resource set.
     * @param resource the used resource set for the operator representation
     * @return the list of converted definitions
     */
    fun stringify(resource: AbstractSignResource): List<String> =
        definitions.map { "${it.effect.stringify(resource)} = ${it.cause.stringify(resource)}" }

    /**
     * Refines the graph with the given refiner.
     * @param refiner the used refiner
     * @return the resulted logical graph
     */
    fun refine(refiner: AbstractRefiner): LogicalGraph {
        return refiner.refine(this)
    }

    /**
     * Gets cause nodes from the definitions.
     * @return all the found node definitions
     */
    fun getCauseNodes(): List<NodeDefinition> {
        return definitions
            .map { collectCauses(it.cause) }
            .flatten()
            .toSet()
            .toList()
            .sortedBy { it.displayName }
    }

    /**
     * Gets effect nodes from the definitions.
     * @return all the found node definitions
     */
    fun getEffectNodes(): List<NodeDefinition> {
        return definitions
            .map { it.effect }
            .sortedBy { it.displayName }
    }

    private fun collectCauses(definition: LogicalDefinition): Set<NodeDefinition> {
        return when (definition) {
            is NodeDefinition -> setOf(definition)
            is OrDefinition -> definition.definitions.map { collectCauses(it) }.flatten().toSet()
            is AndDefinition -> definition.definitions.map { collectCauses(it) }.flatten().toSet()
            is NotDefinition -> collectCauses(definition.inner)
        }
    }
}

