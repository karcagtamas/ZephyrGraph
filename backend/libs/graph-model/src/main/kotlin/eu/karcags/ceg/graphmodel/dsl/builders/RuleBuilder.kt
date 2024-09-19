package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Definition
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule
import eu.karcags.ceg.graphmodel.dsl.markers.GraphDsl

@GraphDsl
class RuleBuilder : AbstractBuilder<Rule>() {
    companion object {
        val DEFAULT_NODE = Node("DEFAULT", null, null)
        val DEFAULT_EFFECT = Node.Effect(
            "EFFECT",
            Definition(null, "This is good"),
            null
        )
    }

    var cause: Node = DEFAULT_NODE
    var effect: Node.Effect = DEFAULT_EFFECT

    override fun build() = Rule(cause, effect)
}