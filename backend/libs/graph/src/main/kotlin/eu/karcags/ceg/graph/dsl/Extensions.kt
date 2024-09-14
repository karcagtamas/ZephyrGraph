package eu.karcags.ceg.graph.dsl

import eu.karcags.ceg.graph.models.Definition
import eu.karcags.ceg.graph.models.Graph
import eu.karcags.ceg.graph.models.Node
import eu.karcags.ceg.graph.models.Rule
import eu.karcags.ceg.graph.dsl.builders.*
import eu.karcags.ceg.graph.dsl.markers.GraphDsl

fun graph(initializer: (@GraphDsl GraphBuilder).() -> Unit): Graph {
    return GraphBuilder().apply { initializer() }.validateAndBuild()
}

fun GraphBuilder.rule(initializer: RuleBuilder.() -> Unit): Rule {
    return RuleBuilder().apply { initializer() }.validateAndBuild().also { addRule(it) }
}

fun cause(initializer: CauseNodeBuilder.() -> Unit): Node.Cause {
    return CauseNodeBuilder().apply { initializer() }.validateAndBuild()
}

fun RuleBuilder.effect(initializer: EffectNodeBuilder.() -> Unit): Node.Effect {
    return EffectNodeBuilder().apply { initializer() }.validateAndBuild().also { effect = it }
}

fun CauseNodeBuilder.expression(initializer: () -> String): Definition {
    return ExpressionBuilder().apply { expression = initializer() }.validateAndBuild().also { definition = it }
}

fun EffectNodeBuilder.statement(initializer: () -> String): Definition {
    return StatementBuilder().apply { statement = initializer() }.validateAndBuild().also { definition = it }
}

infix fun Node.and(other: Node): Node.BinaryAction {
    return Node.BinaryAction.And(this, other)
}

infix fun Node.or(other: Node): Node.BinaryAction {
    return Node.BinaryAction.Or(this, other)
}

operator fun Node.not(): Node.UnaryAction {
    return Node.UnaryAction.Not(this)
}

infix fun Node.to(effect: Node.Effect): Rule {
    return Rule(this, effect)
}