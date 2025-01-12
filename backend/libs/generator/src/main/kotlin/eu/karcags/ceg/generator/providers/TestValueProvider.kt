package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

/**
 * Test value provider for specific test types and operator.
 * @property testType the test type
 * @property operator the expression operator
 * @constructor creates a test value provider
 * @param testType the test type
 * @param operator the expression operator
 * @param T the type of the desired value
 */
abstract class TestValueProvider<T>(val testType: TestType, val operator: Operator) {

    /**
     * Gets a value.
     * @param value value
     * @return a suitable test value
     */
    abstract fun get(value: T): T
}