package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

class DoubleValueProvider(testType: TestType, operator: Operator) : NumberValueProvider<Double>(testType, operator) {

    override fun plus(left: Double, right: Double): Double = left + right

    override fun minus(left: Double, right: Double): Double = left - right

    override fun parse(number: Int): Double = number.toDouble()
}