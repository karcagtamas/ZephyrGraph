package eu.karcags.ceg.graphmodel.dsl

import eu.karcags.ceg.graphmodel.Definition
import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule
import eu.karcags.ceg.graphmodel.dsl.builders.CauseNodeBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.EffectNodeBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.ExpressionBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.GraphBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.RuleBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.StatementBuilder
import eu.karcags.ceg.graphmodel.dsl.markers.GraphDsl

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