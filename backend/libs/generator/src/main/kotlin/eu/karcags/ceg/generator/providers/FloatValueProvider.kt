package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

/**
 * Float test value provider.
 * @constructor creates a [Float] test value provider
 * @param testType the test type
 * @param operator the expression operator
 */
class FloatValueProvider(testType: TestType, operator: Operator) : NumberValueProvider<Float>(testType, operator) {

    override fun plus(left: Float, right: Float): Float = left + right

    override fun minus(left: Float, right: Float): Float = left - right

    override fun parse(number: Int): Float = number.toFloat()
}