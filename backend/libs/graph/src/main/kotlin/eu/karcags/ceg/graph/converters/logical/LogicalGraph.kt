package eu.karcags.ceg.graph.converters.logical

import eu.karcags.ceg.graph.converters.logical.definitions.LogicalDefinition
import kotlinx.serialization.Serializable

@Serializable
class LogicalGraph(val definitions: List<LogicalDefinition>) {

    override fun toString(): String {
        return definitions.joinToString("\n")
    }
}

