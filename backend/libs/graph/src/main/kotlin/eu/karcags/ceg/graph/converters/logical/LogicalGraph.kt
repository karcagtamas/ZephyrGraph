package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import kotlinx.serialization.Serializable

@Serializable
class LogicalGraph(val definitions: List<LogicalDefinition>) {

    fun stringify(resource: AbstractSignResource): List<String> = definitions.map { it.stringify(resource) }
}

