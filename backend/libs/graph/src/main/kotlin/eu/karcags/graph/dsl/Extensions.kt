package eu.karcags.graph.dsl

import eu.karcags.graph.Definition
import eu.karcags.graph.Graph
import eu.karcags.graph.Node
import eu.karcags.graph.Rule
import eu.karcags.graph.dsl.builders.*
import eu.karcags.graph.dsl.markers.GraphDsl

fun graph(initializer: (@GraphDsl GraphBuilder).() -> Unit): Graph {
    return GraphBuilder().apply { initializer() }.validateAndBuild()
}

fun GraphBuilder.rule(initializer: RuleBuilder.() -> Unit): Rule {
    return RuleBuilder().apply { initializer() }.validateAndBuild().also { addRule(it) }
}

fun cause(initializer: CauseNodeBuilder.() -> Unit): Node.CauseNode {
    return CauseNodeBuilder().apply { initializer() }.validateAndBuild()
}

fun effect(initializer: EffectNodeBuilder.() -> Unit): Node.EffectNode {
    return EffectNodeBuilder().apply { initializer() }.validateAndBuild()
}

fun expression(initializer: ExpressionBuilder.() -> Unit): Definition {
    return ExpressionBuilder().apply { initializer() }.validateAndBuild()
}

fun statement(initializer: StatementBuilder.() -> Unit): Definition {
    return StatementBuilder().apply { initializer() }.validateAndBuild()
}

infix fun Node.and(other: Node): Node.BiActionNode {
    return Node.BiActionNode.AndNode(this, other)
}

infix fun Node.or(other: Node): Node.BiActionNode {
    return Node.BiActionNode.OrNode(this, other)
}

operator fun Node.not(): Node.UnActionNode {
    return Node.UnActionNode.NotNode(this)
}

infix fun Node.to(target: Node.EffectNode): Rule {
    return Rule(this, target)
}