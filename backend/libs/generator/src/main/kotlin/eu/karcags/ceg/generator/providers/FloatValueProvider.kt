package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

class FloatValueProvider(testType: TestType, operator: Operator) : NumberValueProvider<Float>(testType, operator) {

    override fun plus(left: Float, right: Float): Float = left + right

    override fun minus(left: Float, right: Float): Float = left - right

    override fun parse(number: Int): Float = number.toFloat()
}