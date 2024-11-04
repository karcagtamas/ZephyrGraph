package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

abstract class NumberValueProvider<T : Number>(testType: TestType, operator: Operator) :
    TestValueProvider<T>(testType, operator) {
    override fun get(value: T): T {
        return when (testType) {
            TestType.ON -> when (operator) {
                is Operator.LessThan -> minus(value, parse(1))
                is Operator.GreaterThan -> plus(value, parse(1))
                else -> value
            }

            TestType.OFF -> when (operator) {
                is Operator.LessThanOrEqual -> plus(value, parse(1))
                is Operator.GreaterThanOrEqual -> minus(value, parse(1))
                else -> value
            }

            TestType.IN -> when (operator) {
                is Operator.LessThan -> minus(value, parse(1000))
                is Operator.GreaterThan -> plus(value, parse(1000))
                is Operator.LessThanOrEqual -> minus(value, parse(1000))
                is Operator.GreaterThanOrEqual -> plus(value, parse(1000))
                else -> value
            }

            TestType.OUT -> when (operator) {
                is Operator.LessThan -> plus(value, parse(1000))
                is Operator.GreaterThan -> minus(value, parse(1000))
                is Operator.LessThanOrEqual -> plus(value, parse(1000))
                is Operator.GreaterThanOrEqual -> minus(value, parse(1000))
                else -> value
            }

            else -> value
        }
    }

    abstract fun plus(left: T, right: T): T

    abstract fun minus(left: T, right: T): T

    abstract fun parse(number: Int): T
}