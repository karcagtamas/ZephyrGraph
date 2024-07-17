package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Node
import eu.karcags.graph.Rule

class RuleBuilder {
    companion object {
        val DEFAULT_NODE = Node("DEFAULT")
        val DEFAULT_EFFECT = Node.EffectNode("EFFECT")
    }

    var source: Node = DEFAULT_NODE
    var target: Node.EffectNode = DEFAULT_EFFECT
    

    fun build() = Rule(source, target)
}