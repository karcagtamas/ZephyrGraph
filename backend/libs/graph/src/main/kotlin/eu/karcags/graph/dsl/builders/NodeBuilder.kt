package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Definition
import eu.karcags.graph.Node

abstract class NodeBuilder<T> : AbstractBuilder<T>() where T : Node {
    var displayName: String = ""
    open var definition: Definition? = null
    var description: String? = null
}