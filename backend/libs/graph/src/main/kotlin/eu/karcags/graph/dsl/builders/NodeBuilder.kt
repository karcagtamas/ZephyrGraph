package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Node

abstract class NodeBuilder : AbstractBuilder<Node>() {
    var displayName: String = ""

    override fun build() = Node(displayName)
}