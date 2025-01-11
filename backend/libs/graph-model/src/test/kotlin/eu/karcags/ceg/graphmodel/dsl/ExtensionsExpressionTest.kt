package eu.karcags.ceg.graphmodel.dsl

import eu.karcags.ceg.graphmodel.expressions.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExtensionsExpressionTest {

    @Test
    fun shouldLitConstructAnOperandWithInt() {
        val result = lit(12)
        val expected = Literal(12)

        assertEquals(expected, result)
    }

    @Test
    fun shouldLitConstructAnOperandWithBoolean() {
        val result = lit(true)
        val expected = Literal(true)

        assertEquals(expected, result)
    }

    @Test
    fun shouldLitConstructAnOperandWithFloat() {
        val result = lit(1.1f)
        val expected = Literal(1.1f)

        assertEquals(expected, result)
    }

    @Test
    fun shouldEqConstructAnExpression() {
        val result = lit(1.1f) eq Variable("alma", Int::class)
        val expected = LogicalExpression(Literal(1.1f), Variable("alma", Int::class), Operator.Equal)

        assertEquals(expected, result)
    }

    @Test
    fun shouldLogicalExpressionOrderedTheExpression() {
        val result = (lit(1.1f) eq Variable("alma", Int::class)).ordered()
        val expected = LogicalExpression(Variable("alma", Int::class), Literal(1.1f), Operator.Equal)

        assertEquals(expected, result)
    }

    @Test
    fun shouldLogicalExpressionSimplifyTheExpression() {
        val result = (Variable("alma", Int::class) eq (lit(1.1f) + lit(1.1f))).simplified()
        val expected = LogicalExpression(Variable("alma", Int::class), Literal(2.2f), Operator.Equal)

        assertEquals(expected, result)
    }

    @Test
    fun shouldLtConstructAnExpression() {
        val result = lit(1) lt Variable("a", Int::class)
        val expected = LogicalExpression(Literal(1), Variable("a", Int::class), Operator.LessThan)

        assertEquals(expected, result)
    }

    @Test
    fun shouldLteConstructAnExpression() {
        val result = lit(10) lte Variable("a", Int::class)
        val expected = LogicalExpression(Literal(10), Variable("a", Int::class), Operator.LessThanOrEqual)

        assertEquals(expected, result)
    }

    @Test
    fun shouldGtConstructAnExpression() {
        val result = Variable("b", Int::class) gt Variable("a", Int::class)
        val expected = LogicalExpression(Variable("b", Int::class), Variable("a", Int::class), Operator.GreaterThan)

        assertEquals(expected, result)
    }

    @Test
    fun shouldGteConstructAnExpression() {
        val result = Variable("c", Int::class) gte lit(100)
        val expected = LogicalExpression(Variable("c", Int::class), Literal(100), Operator.GreaterThanOrEqual)

        assertEquals(expected, result)
    }

    @Test
    fun shouldIsInConstructAnExpression() {
        val result = Variable("c", Int::class) isIn 1..10
        val expected = LogicalExpression(Variable("c", Int::class), ClosedRangeLiteral(1..10), Operator.InInterval)

        assertEquals(expected, result)
    }

    @Test
    fun shouldIsNotInConstructAnExpression() {
        val result = Variable("c", Int::class) isNotIn 1f..<10f
        val expected = LogicalExpression(Variable("c", Int::class), OpenEndRangeLiteral(1f..<10f), Operator.NotInInterval)

        assertEquals(expected, result)
    }

    @Test
    fun shouldEqWithBooleanCauseIsTrue() {
        val result = Variable("c", Boolean::class) eq true
        val expected = LogicalExpression(Variable("c", Boolean::class), Literal(true), Operator.IsTrue)

        assertEquals(expected, result)
    }

    @Test
    fun shouldEqWithBooleanCauseIsFalse() {
        val result = Variable("c", Boolean::class) eq false
        val expected = LogicalExpression(Variable("c", Boolean::class), Literal(false), Operator.IsFalse)

        assertEquals(expected, result)
    }

    @Test
    fun shouldNotEqWithBooleanCauseIsFalse() {
        val result = Variable("c", Boolean::class) neq true
        val expected = LogicalExpression(Variable("c", Boolean::class), Literal(true), Operator.IsFalse)

        assertEquals(expected, result)
    }

    @Test
    fun shouldNotEqWithBooleanCauseIsTrue() {
        val result = Variable("c", Boolean::class) neq false
        val expected = LogicalExpression(Variable("c", Boolean::class), Literal(false), Operator.IsTrue)

        assertEquals(expected, result)
    }
}