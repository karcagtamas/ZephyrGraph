package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

/**
 * Boolean test value provider.
 * @constructor creates a [Boolean] test value provider
 * @param testType the test type
 * @param operator the expression operator
 */
class BooleanValueProvider(testType: TestType, operator: Operator) : TestValueProvider<Boolean>(testType, operator) {

    override fun get(value: Boolean): Boolean {
        return when (testType) {
            TestType.TRUE -> true
            TestType.FALSE -> false
            else -> value
        }
    }
}