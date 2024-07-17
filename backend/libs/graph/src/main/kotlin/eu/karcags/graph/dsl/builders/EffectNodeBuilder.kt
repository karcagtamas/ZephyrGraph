package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Node

class EffectNodeBuilder : NodeBuilder() {

    override fun build() = Node.EffectNode(displayName)
}