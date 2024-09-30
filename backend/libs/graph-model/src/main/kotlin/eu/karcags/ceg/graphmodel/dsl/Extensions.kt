package eu.karcags.ceg.graphmodel.dsl

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule
import eu.karcags.ceg.graphmodel.dsl.builders.AndNodeBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.CauseNodeBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.EffectNodeBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.ExpressionBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.GraphBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.NotNodeBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.OrNodeBuilder
import eu.karcags.ceg.graphmodel.dsl.builders.RuleBuilder
import eu.karcags.ceg.graphmodel.dsl.markers.GraphDsl

fun graph(initializer: (@GraphDsl GraphBuilder).() -> Unit): Graph {
    return GraphBuilder().apply { initializer() }.validateAndBuild()
}

fun GraphBuilder.rule(initializer: RuleBuilder.() -> Unit): Rule {
    return RuleBuilder(nextRuleId(), getGraphNodes()).apply { initializer() }.validateAndBuild().also { addRule(it) }
}

fun GraphBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> String): Node.Cause {
    return CauseNodeBuilder(displayName).apply { expression = initializer() }.validateAndBuild().also { addNode(it) }
}

fun RuleBuilder.effect(initializer: EffectNodeBuilder.() -> String): Node.Effect {
    return EffectNodeBuilder(id).apply { expression = initializer() }.validateAndBuild().also { effect = it }
}

fun RuleBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { cause = it }
}

fun RuleBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { cause = it }
}

fun RuleBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { cause = it }
}

fun RuleBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> String): Node.Cause {
    return CauseNodeBuilder(displayName).apply { expression = initializer() }.validateAndBuild().also { cause = it }
}

fun RuleBuilder.causeById(displayName: String): Node.Cause {
    return graphNodes.filter { it.displayName == displayName }.first().also { cause = it }
}

fun AndNodeBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun AndNodeBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun AndNodeBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun AndNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> String): Node.Cause {
    return CauseNodeBuilder(displayName).apply { expression = initializer() }.validateAndBuild().also { addNode(it) }
}

fun AndNodeBuilder.causeById(displayName: String): Node.Cause {
    return graphNodes.filter { it.displayName == displayName }.first().also { addNode(it) }
}

fun OrNodeBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun OrNodeBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun OrNodeBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun OrNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> String): Node.Cause {
    return CauseNodeBuilder(displayName).apply { expression = initializer() }.validateAndBuild().also { addNode(it) }
}

fun OrNodeBuilder.causeById(displayName: String): Node.Cause {
    return graphNodes.filter { it.displayName == displayName }.first().also { addNode(it) }
}

fun NotNodeBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { node = it }
}

fun NotNodeBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { node = it }
}

fun NotNodeBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(graphNodes).apply { initializer() }.validateAndBuild().also { node = it }
}

fun NotNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> String): Node.Cause {
    return CauseNodeBuilder(displayName).apply { expression = initializer() }.validateAndBuild().also { node = it }
}

fun NotNodeBuilder.causeById(displayName: String): Node.Cause {
    return graphNodes.filter { it.displayName == displayName }.first().also { node = it }
}

fun CauseNodeBuilder.expression(initializer: () -> String): String {
    return ExpressionBuilder().apply { expression = initializer() }.validateAndBuild().also { expression = it }
}

fun EffectNodeBuilder.expression(initializer: () -> String): String {
    return ExpressionBuilder().apply { expression = initializer() }.validateAndBuild().also { expression = it }
}

infix fun Node.and(other: Node): Node.BinaryAction {
    return Node.BinaryAction.And(setOf(this, other))
}

infix fun Node.or(other: Node): Node.BinaryAction {
    return Node.BinaryAction.Or(setOf(this, other))
}

operator fun Node.not(): Node.UnaryAction {
    return Node.UnaryAction.Not(this)
}