package eu.karcags.ceg.graph.dsl

import eu.karcags.ceg.graph.Definition
import eu.karcags.ceg.graph.Graph
import eu.karcags.ceg.graph.Node
import eu.karcags.ceg.graph.Rule
import eu.karcags.ceg.graph.dsl.builders.*
import eu.karcags.ceg.graph.dsl.markers.GraphDsl

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

fun CauseNodeBuilder.expression(initializer: () -> String): Definition {
    return ExpressionBuilder().apply { expression = initializer() }.validateAndBuild().also { definition = it }
}

fun EffectNodeBuilder.statement(initializer: () -> String): Definition {
    return StatementBuilder().apply { statement = initializer() }.validateAndBuild().also { definition = it }
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