package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition
import eu.karcags.ceg.graph.converters.logical.refiners.AbstractRefiner
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import kotlinx.serialization.Serializable

@Serializable
class LogicalGraph(val definitions: List<Pair<NodeDefinition, LogicalDefinition>>) {

    fun stringify(resource: AbstractSignResource): List<String> = definitions.map { "${it.first.stringify(resource)} = ${it.second.stringify(resource)}" }

    fun refine(refiner: AbstractRefiner): LogicalGraph {
        return refiner.refine(this)
    }
}

