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
import eu.karcags.ceg.graphmodel.dsl.builders.DescriptionBuilder
import eu.karcags.ceg.graphmodel.dsl.markers.GraphDsl
import eu.karcags.ceg.graphmodel.expressions.Expression
import eu.karcags.ceg.graphmodel.expressions.Literal
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import eu.karcags.ceg.graphmodel.expressions.Operand
import eu.karcags.ceg.graphmodel.expressions.Operator
import eu.karcags.ceg.graphmodel.expressions.Variable

fun graph(initializer: (@GraphDsl GraphBuilder).() -> Unit): Graph {
    return GraphBuilder().apply { initializer() }.validateAndBuild()
}

fun GraphBuilder.rule(initializer: RuleBuilder.() -> Unit): Rule {
    return RuleBuilder(nextRuleId(), getGraphNodes()).apply { initializer() }.validateAndBuild().also { addRule(it) }
}

fun GraphBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName).apply { expression = initializer() }.validateAndBuild().also { addNode(it) }
}

fun RuleBuilder.effect(initializer: EffectNodeBuilder.() -> String): Node.Effect {
    return EffectNodeBuilder(id).apply { description = initializer() }.validateAndBuild().also { effect = it }
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

fun RuleBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
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

fun AndNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
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

fun OrNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
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

fun NotNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName).apply { expression = initializer() }.validateAndBuild().also { node = it }
}

fun NotNodeBuilder.causeById(displayName: String): Node.Cause {
    return graphNodes.filter { it.displayName == displayName }.first().also { node = it }
}

fun CauseNodeBuilder.expression(initializer: () -> LogicalExpression): LogicalExpression {
    return ExpressionBuilder().apply { expression = initializer() }.validateAndBuild().also { expression = it }
}

fun EffectNodeBuilder.description(initializer: () -> String): String {
    return DescriptionBuilder().apply { description = initializer() }.validateAndBuild().also { description = it }
}

fun lit(value: Int): Literal<Int> = Literal(value)

fun lit(value: Boolean): Literal<Boolean> = Literal(value)

fun lit(value: Double): Literal<Double> = Literal(value)

fun lit(value: String): Literal<String> = Literal(value)

fun variable(name: String): Variable = Variable(name.trim().replace(" ", ""))

infix fun Operand.eq(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.Equal)

infix fun Operand.neq(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.NotEqual)

infix fun Operand.lt(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.LessThan)

infix fun Operand.lte(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.LessThanOrEqual)

infix fun Operand.gt(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.GreaterThan)

infix fun Operand.gte(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.GreaterThanOrEqual)

operator fun Operand.plus(other: Operand): Expression = Expression(this, other, Operator.Plus)

operator fun Operand.minus(other: Operand): Expression = Expression(this, other, Operator.Minus)

operator fun Operand.times(other: Operand): Expression = Expression(this, other, Operator.Times)

operator fun Operand.div(other: Operand): Expression = Expression(this, other, Operator.Division)