package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Node

abstract class NodeBuilder {
    var displayName: String = ""
    
    open fun build() = Node(displayName)
}