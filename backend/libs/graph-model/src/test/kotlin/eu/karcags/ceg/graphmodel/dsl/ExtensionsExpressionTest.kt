package eu.karcags.ceg.graphmodel.dsl

import eu.karcags.ceg.graphmodel.expressions.Literal
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import eu.karcags.ceg.graphmodel.expressions.Operator
import eu.karcags.ceg.graphmodel.expressions.Variable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExtensionsExpressionTest {

    @Test
    fun shouldVariableConstructAnOperand() {
        val result = variable("a")
        val expected = Variable("a")

        assertEquals(expected, result)
    }

    @Test
    fun shouldLitConstructAnOperandWithString() {
        val result = lit("string_value")
        val expected = Literal("string_value")

        assertEquals(expected, result)
    }

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
        val result = lit(1.1)
        val expected = Literal(1.1)

        assertEquals(expected, result)
    }

    @Test
    fun shouldEqConstructAnExpression() {
        val result = lit(1.1) eq variable("alma")
        val expected = LogicalExpression(Literal(1.1), Variable("alma"), Operator.Equal)

        assertEquals(expected, result)
    }

    @Test
    fun shouldNeqConstructAnExpression() {
        val result = lit("korte") neq variable("alma")
        val expected = LogicalExpression(Literal("korte"), Variable("alma"), Operator.NotEqual)

        assertEquals(expected, result)
    }

    @Test
    fun shouldLtConstructAnExpression() {
        val result = lit(1) lt variable("a")
        val expected = LogicalExpression(Literal(1), Variable("a"), Operator.LessThan)

        assertEquals(expected, result)
    }

    @Test
    fun shouldLteConstructAnExpression() {
        val result = lit(10) lte variable("a")
        val expected = LogicalExpression(Literal(10), Variable("a"), Operator.LessThanOrEqual)

        assertEquals(expected, result)
    }

    @Test
    fun shouldGtConstructAnExpression() {
        val result = variable("b") gt variable("a")
        val expected = LogicalExpression(Variable("b"), Variable("a"), Operator.GreaterThan)

        assertEquals(expected, result)
    }

    @Test
    fun shouldGteConstructAnExpression() {
        val result = variable("c") gte lit(100)
        val expected = LogicalExpression(Variable("c"), Literal(100), Operator.GreaterThanOrEqual)

        assertEquals(expected, result)
    }
}