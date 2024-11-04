package eu.karcags.ceg.generator

import eu.karcags.ceg.common.changeIndex
import eu.karcags.ceg.generator.providers.BooleanValueProvider
import eu.karcags.ceg.generator.providers.DoubleValueProvider
import eu.karcags.ceg.generator.providers.IdentityValueProvider
import eu.karcags.ceg.generator.providers.IntValueProvider
import eu.karcags.ceg.graphmodel.expressions.Literal
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import eu.karcags.ceg.graphmodel.expressions.Operator
import eu.karcags.ceg.graphmodel.expressions.Variable
import kotlinx.serialization.Serializable

class BVA {

    fun construct(items: List<LogicalExpression>): FinalResult {
        val interval = items.filter { it.type() == DefinitionType.Intervals }
            .foldIndexed(Interval(0, emptyList())) { idx, acc, _ -> acc.increase(idx) }

        val results = mutableListOf<Result>()
        results.add(items.phase1())

        val base = items.map {
            val type = when (it.type()) {
                DefinitionType.Equal -> TestType.ON
                DefinitionType.NotEqual -> TestType.IN
                DefinitionType.IsTrue -> TestType.TRUE
                DefinitionType.IsFalse -> TestType.FALSE
                DefinitionType.Intervals -> TestType.IN
                else -> TestType.IN
            }

            Pair(type, it)
        }

        results.add(items.phase2())
        results.addAll(items.phase3(base))
        results.addAll(phase4(interval, base))

        return FinalResult(items.size, items.map { it.toString() }, results)
    }

    private fun List<LogicalExpression>.phase1(): Result {
        return map {
            val type = when (it.type()) {
                DefinitionType.Equal -> TestType.ON
                DefinitionType.NotEqual -> TestType.IN2
                DefinitionType.IsTrue -> TestType.TRUE
                DefinitionType.IsFalse -> TestType.FALSE
                DefinitionType.Intervals -> TestType.ON1
                else -> TestType.ININ
            }

            Pair(type, it)
        }.map {
            Test.from(it)
        }.let { Result(this.size, it) }
    }

    private fun List<LogicalExpression>.phase2(): Result {
        return map {
            val type = when (it.type()) {
                DefinitionType.NotEqual -> TestType.IN1
                DefinitionType.IsTrue -> TestType.TRUE
                DefinitionType.IsFalse -> TestType.FALSE
                DefinitionType.Intervals -> TestType.ON2
                else -> TestType.ON
            }

            Pair(type, it)
        }.map {
            Test.from(it)
        }.let { Result(this.size, it) }
    }

    private fun List<LogicalExpression>.phase3(base: List<Pair<TestType, LogicalExpression>>): List<Result> {
        return mapIndexed { idx, it ->
            when (it.type()) {
                DefinitionType.Equal -> listOf(
                    base.changeIndex(idx, Pair(TestType.OUT1, it)),
                    base.changeIndex(idx, Pair(TestType.OUT2, it))
                )

                DefinitionType.NotEqual -> listOf(base.changeIndex(idx, Pair(TestType.OFF, it)))
                DefinitionType.IsTrue -> listOf(base.changeIndex(idx, Pair(TestType.FALSE, it)))
                DefinitionType.IsFalse -> listOf(base.changeIndex(idx, Pair(TestType.TRUE, it)))
                else -> listOf(
                    base.changeIndex(idx, Pair(TestType.OFF, it)),
                    base.changeIndex(idx, Pair(TestType.OUT, it))
                )
            }
        }.flatten().map { list -> Result(this.size, list.map { Test.from(it) }) }
    }

    private fun phase4(interval: Interval, base: List<Pair<TestType, LogicalExpression>>): List<Result> {
        if (interval.number > 0) {
            return interval.values.map { v ->
                listOf(TestType.OUT1, TestType.OUT2, TestType.OFF1, TestType.OFF2)
                    .map { type ->
                        base.changeIndex(v, Pair(type, base[v].second))
                    }
                    .flatten()
                    .let { list -> Result(base.size, list.map { Test.from(it) }) }
            }
        }

        return emptyList()
    }

    private fun LogicalExpression.type(): DefinitionType {
        return when (operator) {
            is Operator.Equal -> DefinitionType.Equal
            is Operator.NotEqual -> DefinitionType.NotEqual
            is Operator.LessThan -> DefinitionType.LessThan
            is Operator.LessThanOrEqual -> DefinitionType.LessThanOrEqualTo
            is Operator.GreaterThan -> DefinitionType.GreaterThan
            is Operator.GreaterThanOrEqual -> DefinitionType.GreaterThanOrEqualTo
            is Operator.IsTrue -> DefinitionType.IsTrue
            is Operator.IsFalse -> DefinitionType.IsFalse
            else -> throw IllegalArgumentException("Unknown operator $operator")
        }
    }


    data class Interval(val number: Int, val values: List<Int>) {
        fun increase(value: Int): Interval {
            return Interval(value + 1, values + value)
        }
    }

    @Serializable
    data class FinalResult(val count: Int, val expressions: List<String>, val results: List<Result>)

    @Serializable
    data class Result(val count: Int, val test: List<Test>)

    @Serializable
    data class Test(
        val type: TestType,
        val base: String,
        val value: Value,
        val example: String,
        val expectation: Boolean
    ) {
        companion object {
            fun from(type: TestType, expression: LogicalExpression): Test {
                if (expression.left is Variable && expression.right is Literal<*>) {
                    val variable = expression.left as Variable
                    val literal = expression.right as Literal<*>

                    val value: Any = when (literal.value) {
                        is Int -> IntValueProvider(type, expression.operator).get(literal.value as Int)
                        is Double -> DoubleValueProvider(type, expression.operator).get(literal.value as Double)
                        is Boolean -> BooleanValueProvider(type, expression.operator).get(literal.value as Boolean)
                        else -> IdentityValueProvider<Any>(type, expression.operator).get(literal.value as Any)
                    }

                    return Test(
                        type,
                        expression.toString(),
                        Value(variable.name, literal.getType().toString(), value.toString()),
                        LogicalExpression(Literal(value), literal, expression.operator).toString(),
                        type.expectation(),
                    )
                }

                throw RuntimeException("Left operand must be a variable and the right operand must be a literal value for each logical expression")
            }

            fun from(pair: Pair<TestType, LogicalExpression>): Test {
                return from(pair.first, pair.second)
            }
        }

        @Serializable
        data class Value(val name: String, val type: String, val value: String)
    }
}