package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Definition
import eu.karcags.graph.Node
import eu.karcags.graph.Rule
import eu.karcags.graph.dsl.markers.GraphDsl

@GraphDsl
class RuleBuilder : AbstractBuilder<Rule>() {
    companion object {
        val DEFAULT_NODE = Node("DEFAULT", null, null)
        val DEFAULT_EFFECT = Node.EffectNode("EFFECT", Definition(null, "This is good"), null)
    }

    var source: Node = DEFAULT_NODE
    var target: Node.EffectNode = DEFAULT_EFFECT

    override fun build() = Rule(source, target)
}