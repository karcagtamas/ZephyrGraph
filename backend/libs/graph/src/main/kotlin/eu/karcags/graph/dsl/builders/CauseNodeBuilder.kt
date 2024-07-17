package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Node

class CauseNodeBuilder : NodeBuilder() {
    override fun build() = Node.CauseNode(displayName)
}