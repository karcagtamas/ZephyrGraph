package eu.karcags.ceg.graphmodel.dsl

import eu.karcags.ceg.graphmodel.expressions.Literal
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import eu.karcags.ceg.graphmodel.expressions.Operator
import eu.karcags.ceg.graphmodel.expressions.Variable
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
    fun shouldLitConstructAnOperandWithDouble() {
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
}