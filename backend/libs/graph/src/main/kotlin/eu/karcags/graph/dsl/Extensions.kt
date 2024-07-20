package eu.karcags.graph.dsl

import eu.karcags.graph.Graph
import eu.karcags.graph.Node
import eu.karcags.graph.Rule
import eu.karcags.graph.dsl.builders.CauseNodeBuilder
import eu.karcags.graph.dsl.builders.EffectNodeBuilder
import eu.karcags.graph.dsl.builders.RuleBuilder
import eu.karcags.graph.dsl.builders.GraphBuilder
import eu.karcags.graph.dsl.markers.GraphDsl

fun graph(initializer: (@GraphDsl GraphBuilder).() -> Unit): Graph {
    return GraphBuilder().apply { initializer() }.build()
}

fun GraphBuilder.rule(initializer: RuleBuilder.() -> Unit): Rule {
    return RuleBuilder().apply { initializer() }.build().also { addRule(it) }
}

fun cause(initializer: CauseNodeBuilder.() -> Unit): Node.CauseNode {
    return CauseNodeBuilder().apply { initializer() }.build()
}

fun effect(initializer: EffectNodeBuilder.() -> Unit): Node.EffectNode {
    return EffectNodeBuilder().apply { initializer() }.build()
}

infix fun Node.and(other: Node): Node.ActionNode {
    return Node.ActionNode.AndNode(this, other)
}

infix fun Node.or(other: Node): Node.ActionNode {
    return Node.ActionNode.OrNode(this, other)
}

infix fun Node.to(target: Node.EffectNode): Rule {
    return Rule(this, target)
}