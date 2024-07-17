package eu.karcags.graph.dsl

import eu.karcags.graph.Graph
import eu.karcags.graph.Node
import eu.karcags.graph.Rule
import eu.karcags.graph.dsl.builders.CauseNodeBuilder
import eu.karcags.graph.dsl.builders.EffectNodeBuilder
import eu.karcags.graph.dsl.builders.RuleBuilder
import eu.karcags.graph.dsl.builders.GraphBuilder

fun graph(initalizer: GraphBuilder.() -> Unit): Graph {
    return GraphBuilder().apply { initalizer() }.build()
}

fun GraphBuilder.rule(initalizer: RuleBuilder.() -> Unit): Rule {
    return RuleBuilder().apply { initalizer() }.build().also { addRule(it) }
}

fun GraphBuilder.cause(initalizer: CauseNodeBuilder.() -> Unit): Node.CauseNode {
    return CauseNodeBuilder().apply { initalizer() }.build()
}

fun GraphBuilder.effect(initalizer: EffectNodeBuilder.() -> Unit): Node.EffectNode {
    return EffectNodeBuilder().apply { initalizer() }.build()
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