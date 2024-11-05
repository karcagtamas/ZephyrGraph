package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

class IntValueProvider(testType: TestType, operator: Operator) : NumberValueProvider<Int>(testType, operator) {

    override fun plus(left: Int, right: Int): Int = left + right

    override fun minus(left: Int, right: Int): Int = left - right

    override fun parse(number: Int): Int = number
}