package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

class IdentityValueProvider<T>(testType: TestType, operator: Operator) : TestValueProvider<T>(testType, operator) {

    override fun get(value: T): T = value
}