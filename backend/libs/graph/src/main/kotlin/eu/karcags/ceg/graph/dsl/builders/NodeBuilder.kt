package eu.karcags.ceg.graph.dsl.builders

import eu.karcags.ceg.graph.models.Definition
import eu.karcags.ceg.graph.models.Node

abstract class NodeBuilder<T> : AbstractBuilder<T>() where T : Node {
    var displayName: String = ""
    open var definition: Definition? = null
    var description: String? = null
}