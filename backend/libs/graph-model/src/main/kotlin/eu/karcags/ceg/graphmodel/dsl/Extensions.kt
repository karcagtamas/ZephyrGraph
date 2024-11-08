package eu.karcags.ceg.graphmodel.dsl

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.Rule
import eu.karcags.ceg.graphmodel.dsl.builders.*
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
    return RuleBuilder(nextRuleId(), nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addRule(it) }
}

fun GraphBuilder.variables(initializer: VariablesBuilder.() -> Unit): List<Variable<*>> {
    return VariablesBuilder().apply { initializer() }.validateAndBuild().onEach { variable -> addVariable(variable) }
}

fun VariablesBuilder.int(name: String): Variable<*> {
    return Variable(name, Int::class).also { add(it) }
}

fun VariablesBuilder.boolean(name: String): Variable<*> {
    return Variable(name, Boolean::class).also { add(it) }
}

fun VariablesBuilder.float(name: String, precision: Float = 1f): Variable<*> {
    return Variable(name, Float::class, precision).also { add(it) }
}

fun GraphBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName, variableProvider).apply { expression = initializer() }.validateAndBuild().also { addNode(it) }
}

fun RuleBuilder.effect(initializer: EffectNodeBuilder.() -> String): Node.Effect {
    return EffectNodeBuilder(id).apply { description = initializer() }.validateAndBuild().also { effect = it }
}

fun RuleBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { cause = it }
}

fun RuleBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { cause = it }
}

fun RuleBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { cause = it }
}

fun RuleBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName, variableProvider).apply { expression = initializer() }.validateAndBuild().also { cause = it }
}

fun RuleBuilder.causeById(displayName: String): Node.Cause {
    return nodeProvider.byKey(displayName)?.also { cause = it }!!
}

fun AndNodeBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun AndNodeBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun AndNodeBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun AndNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName, variableProvider).apply { expression = initializer() }.validateAndBuild().also { addNode(it) }
}

fun AndNodeBuilder.causeById(displayName: String): Node.Cause {
    return nodeProvider.byKey(displayName)?.also { addNode(it) }!!
}

fun OrNodeBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun OrNodeBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun OrNodeBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { addNode(it) }
}

fun OrNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName, variableProvider).apply { expression = initializer() }.validateAndBuild().also { addNode(it) }
}

fun OrNodeBuilder.causeById(displayName: String): Node.Cause {
    return nodeProvider.byKey(displayName)?.also { addNode(it) }!!
}

fun NotNodeBuilder.and(initializer: AndNodeBuilder.() -> Unit): Node.BinaryAction.And {
    return AndNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { node = it }
}

fun NotNodeBuilder.or(initializer: OrNodeBuilder.() -> Unit): Node.BinaryAction.Or {
    return OrNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { node = it }
}

fun NotNodeBuilder.not(initializer: NotNodeBuilder.() -> Unit): Node.UnaryAction.Not {
    return NotNodeBuilder(nodeProvider, variableProvider).apply { initializer() }.validateAndBuild().also { node = it }
}

fun NotNodeBuilder.cause(displayName: String, initializer: CauseNodeBuilder.() -> LogicalExpression): Node.Cause {
    return CauseNodeBuilder(displayName, variableProvider).apply { expression = initializer() }.validateAndBuild().also { node = it }
}

fun NotNodeBuilder.causeById(displayName: String): Node.Cause {
    return nodeProvider.byKey(displayName)?.also { node = it }!!
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

fun CauseNodeBuilder.variable(name: String): Variable<*> = variableProvider.byKey(name)!!


infix fun Operand.eq(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.Equal)

infix fun Variable<*>.eq(other: Literal<Boolean>): LogicalExpression {
    return LogicalExpression(this, other, if (other.value) Operator.IsTrue else Operator.IsFalse)
}

infix fun Operand.neq(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.NotEqual)

infix fun Operand.lt(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.LessThan)

infix fun Operand.lte(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.LessThanOrEqual)

infix fun Operand.gt(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.GreaterThan)

infix fun Operand.gte(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.GreaterThanOrEqual)

operator fun Operand.plus(other: Operand): Expression = Expression(this, other, Operator.Plus)

operator fun Operand.minus(other: Operand): Expression = Expression(this, other, Operator.Minus)

operator fun Operand.times(other: Operand): Expression = Expression(this, other, Operator.Times)

operator fun Operand.div(other: Operand): Expression = Expression(this, other, Operator.Division)