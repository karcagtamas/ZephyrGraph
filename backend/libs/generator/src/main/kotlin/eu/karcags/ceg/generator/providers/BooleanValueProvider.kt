package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

class BooleanValueProvider(testType: TestType, operator: Operator) : TestValueProvider<Boolean>(testType, operator) {

    override fun get(value: Boolean): Boolean {
        return when (testType) {
            TestType.TRUE -> true
            TestType.FALSE -> false
            else -> value
        }
    }
}