package eu.karcags.ceg.graphmodel.dsl

import eu.karcags.ceg.graphmodel.dsl.builders.CauseNodeBuilder
import eu.karcags.ceg.graphmodel.expressions.*

/**
 * Constructs an [Int] literal operand.
 * @param value [Int] value
 * @return the constructed [Int] literal
 */
fun lit(value: Int): Literal<Int> = Literal(value)

/**
 * Constructs a [Boolean] literal operand.
 * @param value [Boolean] value
 * @return the constructed [Boolean] literal
 */
fun lit(value: Boolean): Literal<Boolean> = Literal(value)

/**
 * Constructs an [Float] literal operand.
 * @param value [Float] value
 * @return the constructed [Float] literal
 */
fun lit(value: Float): Literal<Float> = Literal(value)

/**
 * Constructs a closed range literal operand.
 * @param value range value
 * @return the constructed closed range literal
 */
fun lit(value: IntRange): ClosedRangeLiteral<Int> = ClosedRangeLiteral(value)

/**
 * Constructs a closed floating range literal operand.
 * @param value [Float] range value
 * @return the constructed closed range literal
 */
fun lit(value: ClosedFloatingPointRange<Float>): ClosedRangeLiteral<Float> = ClosedRangeLiteral(value)

/**
 * Constructs an open-end range literal operand.
 * @param value [Float] range value
 * @return the constructed open-end range literal
 */
fun lit(value: OpenEndRange<Float>): OpenEndRangeLiteral<Float> = OpenEndRangeLiteral(value)

/**
 * Finds a variable operand int the cause node builder variable store.
 * @param name the name of the variable
 * @return the found variable operand
 */
fun CauseNodeBuilder.variable(name: String): Variable<*> = variableProvider.byKey(name)!!

/**
 * Equality expression between two [Operand]s.
 * @param other the other operand
 * @return the constructed [LogicalExpression] between the two operands concatenated with equality operator ([Operator.Equal])
 */
infix fun Operand.eq(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.Equal)

/**
 * Equality expression between a [Variable] and a [Int] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.eq(other: Int): LogicalExpression = this eq lit(other)

/**
 * Equality expression between a [Variable] and a [Boolean] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.eq(other: Boolean): LogicalExpression = this eq lit(other)

/**
 * Equality expression between a [Variable] and a [Float] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.eq(other: Float): LogicalExpression = this eq lit(other)

/**
 * Equality expression between a [Variable] and a [Boolean] [Literal] value.
 * @param other the [Boolean] literal operand
 * @return the constructed [LogicalExpression] between the [Variable] and the [Literal] concatenated with [Operator.IsTrue] or [Operator.IsFalse] (depends on the literal value)
 */
infix fun Variable<*>.eq(other: Literal<Boolean>): LogicalExpression {
    return LogicalExpression(this, other, if (other.value) Operator.IsTrue else Operator.IsFalse)
}

/**
 * Not equality expression between two [Operand]s.
 * @param other the other operand
 * @return the constructed [LogicalExpression] between the two operands concatenated with not equality operator ([Operator.NotEqual])
 */
infix fun Operand.neq(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.NotEqual)

/**
 * Not equality expression between a [Variable] and a [Int] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.neq(other: Int): LogicalExpression = this neq lit(other)

/**
 * Not equality expression between a [Variable] and a [Boolean] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.neq(other: Boolean): LogicalExpression = this neq lit(other)

/**
 * Not equality expression between a [Variable] and a [Float] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.neq(other: Float): LogicalExpression = this neq lit(other)

/**
 * Not equality expression between a [Variable] and a [Boolean] [Literal] value.
 * @param other the [Boolean] literal operand
 * @return the constructed [LogicalExpression] between the [Variable] and the [Literal] concatenated with [Operator.IsFalse] or [Operator.IsTrue] (depends on the literal value)
 */
infix fun Variable<*>.neq(other: Literal<Boolean>): LogicalExpression {
    return LogicalExpression(this, other, if (other.value) Operator.IsFalse else Operator.IsTrue)
}

/**
 * Less than expression between two [Operand]s.
 * @param other the other operand
 * @return the constructed [LogicalExpression] between the two operands concatenated with less than operator ([Operator.LessThan])
 */
infix fun Operand.lt(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.LessThan)

/**
 * Less than expression between a [Variable] and a [Int] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.lt(other: Int): LogicalExpression = this lt lit(other)

/**
 * Less than expression between a [Variable] and a [Float] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.lt(other: Float): LogicalExpression = this lt lit(other)

/**
 * Less than or equal expression between two [Operand]s.
 * @param other the other operand
 * @return the constructed [LogicalExpression] between the two operands concatenated with less than or equal operator ([Operator.LessThanOrEqual])
 */
infix fun Operand.lte(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.LessThanOrEqual)

/**
 * Less than or equal expression between a [Variable] and a [Int] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.lte(other: Int): LogicalExpression = this lte lit(other)

/**
 * Less than or equal expression between a [Variable] and a [Float] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.lte(other: Float): LogicalExpression = this lte lit(other)

/**
 * Greater than expression between two [Operand]s.
 * @param other the other operand
 * @return the constructed [LogicalExpression] between the two operands concatenated with greater than operator ([Operator.GreaterThan])
 */
infix fun Operand.gt(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.GreaterThan)

/**
 * Greater than expression between a [Variable] and a [Int] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.gt(other: Int): LogicalExpression = this gt lit(other)

/**
 * Greater than expression between a [Variable] and a [Float] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.gt(other: Float): LogicalExpression = this gt lit(other)

/**
 * Greater than or equal expression between two [Operand]s.
 * @param other the other operand
 * @return the constructed [LogicalExpression] between the two operands concatenated with greater than or equal operator ([Operator.GreaterThanOrEqual])
 */
infix fun Operand.gte(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.GreaterThanOrEqual)

/**
 * Greater than or equal expression between a [Variable] and a [Int] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.gte(other: Int): LogicalExpression = this gte lit(other)

/**
 * Greater than or equal expression between a [Variable] and a [Float] value.
 * @param other the literal value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.gte(other: Float): LogicalExpression = this gte lit(other)

/**
 * Is in expression between two [Operand]s.
 * @param other the other operand
 * @return the constructed [LogicalExpression] between the two operands concatenated with is in operator ([Operator.InInterval])
 */
infix fun Operand.isIn(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.InInterval)

/**
 * Is in expression between a [Variable] and a range value.
 * @param other the range value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.isIn(other: IntRange): LogicalExpression = this isIn lit(other)

/**
 * Is in expression between a [Variable] and a closed floating range value.
 * @param other the range value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.isIn(other: ClosedFloatingPointRange<Float>): LogicalExpression = this isIn lit(other)

/**
 * Is in expression between a [Variable] and an open-end range value.
 * @param other the range value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.isIn(other: OpenEndRange<Float>): LogicalExpression = this isIn lit(other)

/**
 * Is not in expression between two [Operand]s.
 * @param other the other operand
 * @return the constructed [LogicalExpression] between the two operands concatenated with is not in operator ([Operator.NotInInterval])
 */
infix fun Operand.isNotIn(other: Operand): LogicalExpression = LogicalExpression(this, other, Operator.NotInInterval)

/**
 * Is not in expression between a [Variable] and a range value.
 * @param other the range value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.isNotIn(other: IntRange): LogicalExpression = this isNotIn lit(other)

/**
 * Is not in expression between a [Variable] and a closed floating range value.
 * @param other the range value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.isNotIn(other: ClosedFloatingPointRange<Float>): LogicalExpression = this isNotIn lit(other)

/**
 * Is not in expression between a [Variable] and an open-end range value.
 * @param other the range value
 * @return the constructed [LogicalExpression] between the [Variable] and a constructed [Literal]
 */
infix fun Variable<*>.isNotIn(other: OpenEndRange<Float>): LogicalExpression = this isNotIn lit(other)

/**
 * Plus math expression between two operands.
 * @param other the other operand
 * @return the constructed [Expression] between the two operands concatenated with plus operator ([Operator.Plus])
 */
operator fun Operand.plus(other: Operand): Expression = Expression(this, other, Operator.Plus)

/**
 * Minus math expression between two operands.
 * @param other the other operand
 * @return the constructed [Expression] between the two operands concatenated with minus operator ([Operator.Minus])
 */
operator fun Operand.minus(other: Operand): Expression = Expression(this, other, Operator.Minus)

/**
 * Times math expression between two operands.
 * @param other the other operand
 * @return the constructed [Expression] between the two operands concatenated with times operator ([Operator.Times])
 */
operator fun Operand.times(other: Operand): Expression = Expression(this, other, Operator.Times)

/**
 * Division math expression between two operands.
 * @param other the other operand
 * @return the constructed [Expression] between the two operands concatenated with division operator ([Operator.Division])
 */
operator fun Operand.div(other: Operand): Expression = Expression(this, other, Operator.Division)