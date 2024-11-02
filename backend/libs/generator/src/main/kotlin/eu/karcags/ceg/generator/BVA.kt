package eu.karcags.ceg.generator

import eu.karcags.ceg.common.changeIndex
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import eu.karcags.ceg.graphmodel.expressions.Operator
import kotlinx.serialization.Serializable

class BVA {

    fun construct(items: List<LogicalExpression>): FinalResult {
        val interval = items.filter { it.type() == DefinitionType.Intervals }.foldIndexed(Interval(0, emptyList())) { idx, acc, _ -> acc.increase(idx) }

        val results = mutableListOf<Result>()
        results.add(items.phase1())

        val base = items.map {
            when (it.type()) {
                DefinitionType.Equal -> TestType.ON
                DefinitionType.NotEqual -> TestType.IN
                DefinitionType.IsTrue -> TestType.TRUE
                DefinitionType.IsFalse -> TestType.FALSE
                DefinitionType.Intervals -> TestType.IN
                else -> TestType.IN
            }
        }

        results.add(items.phase2())
        results.addAll(items.phase3(base))
        results.addAll(phase4(interval, base))

        return FinalResult(items.size, items.map { it.toString() }, results)
    }

    private fun List<LogicalExpression>.phase1(): Result {
        return map {
            when (it.type()) {
                DefinitionType.Equal -> TestType.ON
                DefinitionType.NotEqual -> TestType.IN2
                DefinitionType.IsTrue -> TestType.TRUE
                DefinitionType.IsFalse -> TestType.FALSE
                DefinitionType.Intervals -> TestType.ON1
                else -> TestType.ININ
            }
        }.let { Result(this.size, it) }
    }

    private fun List<LogicalExpression>.phase2(): Result {
        return map {
            when (it.type()) {
                DefinitionType.NotEqual -> TestType.IN1
                DefinitionType.IsTrue -> TestType.TRUE
                DefinitionType.IsFalse -> TestType.FALSE
                DefinitionType.Intervals -> TestType.ON2
                else -> TestType.ON
            }
        }.let { Result(this.size, it) }
    }

    private fun List<LogicalExpression>.phase3(base: List<TestType>): List<Result> {
        return mapIndexed { idx, it ->
            when (it.type()) {
                DefinitionType.Equal -> listOf(base.changeIndex(idx, TestType.OUT1), base.changeIndex(idx, TestType.OUT2))
                DefinitionType.NotEqual -> listOf(base.changeIndex(idx, TestType.OFF))
                DefinitionType.IsTrue -> listOf(base.changeIndex(idx, TestType.FALSE))
                DefinitionType.IsFalse -> listOf(base.changeIndex(idx, TestType.TRUE))
                else -> listOf(base.changeIndex(idx, TestType.OFF), base.changeIndex(idx, TestType.OUT))
            }
        }.flatten().map { Result(this.size, it) }
    }

    private fun phase4(interval: Interval, base: List<TestType>): List<Result> {
        if (interval.number > 0) {
            return interval.values.map { v ->
                listOf(TestType.OUT1, TestType.OUT2, TestType.OFF1, TestType.OFF2)
                    .map { type ->
                        base.changeIndex(v, type)
                    }
                    .flatten()
                    .let { Result(base.size, it) }
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
    data class Result(val count: Int, val types: List<TestType>)
}