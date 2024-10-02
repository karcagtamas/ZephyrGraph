package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.graph.converters.logical.definitions.AndDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NotDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.OrDefinition
import eu.karcags.ceg.graph.converters.logical.refiners.AbstractRefiner
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import kotlinx.serialization.Serializable

@Serializable
class LogicalGraph(val definitions: List<Pair<NodeDefinition, LogicalDefinition>>) {

    fun stringify(resource: AbstractSignResource): List<String> =
        definitions.map { "${it.first.stringify(resource)} = ${it.second.stringify(resource)}" }

    fun refine(refiner: AbstractRefiner): LogicalGraph {
        return refiner.refine(this)
    }

    fun getCauseNodes(): List<NodeDefinition> {
        return definitions
            .map { collectCauses(it.second) }
            .flatten()
            .toSet()
            .toList()
            .sortedBy { it.id }
    }

    fun getEffectNodes(): List<NodeDefinition> {
        return definitions
            .map { it.first }
            .sortedBy { it.id }
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

