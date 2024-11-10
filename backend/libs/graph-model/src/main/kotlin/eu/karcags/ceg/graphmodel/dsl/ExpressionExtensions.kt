package eu.karcags.ceg.graphmodel.dsl

import eu.karcags.ceg.graphmodel.dsl.builders.CauseNodeBuilder
import eu.karcags.ceg.graphmodel.expressions.*

fun lit(value: Int): Literal<Int> = Literal(value)

fun lit(value: Boolean): Literal<Boolean> = Literal(value)

fun lit(value: Float): Literal<Float> = Literal(value)

fun lit(value: IntRange): RangeLiteral<Int> = RangeLiteral(value)

fun lit(value: ClosedFloatingPointRange<Float>): RangeLiteral<Float> = RangeLiteral(value)

fun CauseNodeBuilder.variable(name: String): Variable<*> = variableProvider.byKey(name)!!

infix fun Operand.eq(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.Equal)

infix fun Variable<*>.eq(other: Int): LogicalExpression = this eq lit(other)

infix fun Variable<*>.eq(other: Boolean): LogicalExpression = this eq lit(other)

infix fun Variable<*>.eq(other: Float): LogicalExpression = this eq lit(other)

infix fun Variable<*>.eq(other: Literal<Boolean>): LogicalExpression {
    return LogicalExpression(this, other, if (other.value) Operator.IsTrue else Operator.IsFalse)
}

infix fun Operand.neq(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.NotEqual)

infix fun Variable<*>.neq(other: Int): LogicalExpression = this neq lit(other)

infix fun Variable<*>.neq(other: Boolean): LogicalExpression = this neq lit(other)

infix fun Variable<*>.neq(other: Float): LogicalExpression = this neq lit(other)

infix fun Variable<*>.neq(other: Literal<Boolean>): LogicalExpression {
    return LogicalExpression(this, other, if (other.value) Operator.IsFalse else Operator.IsTrue)
}

infix fun Operand.lt(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.LessThan)

infix fun Variable<*>.lt(other: Int): LogicalExpression = this lt lit(other)

infix fun Variable<*>.lt(other: Float): LogicalExpression = this lt lit(other)

infix fun Operand.lte(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.LessThanOrEqual)

infix fun Variable<*>.lte(other: Int): LogicalExpression = this lte lit(other)

infix fun Variable<*>.lte(other: Float): LogicalExpression = this lte lit(other)

infix fun Operand.gt(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.GreaterThan)

infix fun Variable<*>.gt(other: Int): LogicalExpression = this gt lit(other)

infix fun Variable<*>.gt(other: Float): LogicalExpression = this gt lit(other)

infix fun Operand.gte(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.GreaterThanOrEqual)

infix fun Variable<*>.gte(other: Int): LogicalExpression = this gte lit(other)

infix fun Variable<*>.gte(other: Float): LogicalExpression = this gte lit(other)

infix fun Operand.isIn(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.InInterval)

infix fun Variable<*>.isIn(other: IntRange): LogicalExpression = this isIn lit(other)

infix fun Variable<*>.isIn(other: ClosedFloatingPointRange<Float>): LogicalExpression = this isIn lit(other)

infix fun Operand.isNotIn(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.NotInInterval)

infix fun Variable<*>.isNotIn(other: IntRange): LogicalExpression = this isNotIn lit(other)

infix fun Variable<*>.isNotIn(other: ClosedFloatingPointRange<Float>): LogicalExpression = this isNotIn lit(other)

operator fun Operand.plus(other: Operand): Expression = Expression(this, other, Operator.Plus)

operator fun Operand.minus(other: Operand): Expression = Expression(this, other, Operator.Minus)

operator fun Operand.times(other: Operand): Expression = Expression(this, other, Operator.Times)

operator fun Operand.div(other: Operand): Expression = Expression(this, other, Operator.Division)