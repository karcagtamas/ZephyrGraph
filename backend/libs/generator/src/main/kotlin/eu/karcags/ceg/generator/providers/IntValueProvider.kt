package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

class IntValueProvider(testType: TestType, operator: Operator) : TestValueProvider<Int>(testType, operator) {
    override fun get(value: Int): Int {
        return when (testType) {
            TestType.ON -> when (operator) {
                is Operator.LessThan -> value - 1
                is Operator.GreaterThan -> value + 1
                else -> value
            }

            TestType.OFF -> when (operator) {
                is Operator.LessThanOrEqual -> value + 1
                is Operator.GreaterThanOrEqual -> value - 1
                else -> value
            }

            TestType.IN -> when (operator) {
                is Operator.LessThan -> value - 1000
                is Operator.GreaterThan -> value + 1000
                is Operator.LessThanOrEqual -> value - 1000
                is Operator.GreaterThanOrEqual -> value - 1000
                else -> value
            }

            TestType.OUT -> when (operator) {
                is Operator.LessThan -> value + 1000
                is Operator.GreaterThan -> value - 1000
                is Operator.LessThanOrEqual -> value + 1000
                is Operator.GreaterThanOrEqual -> value - 1000
                else -> value
            }

            else -> value
        }
    }
}