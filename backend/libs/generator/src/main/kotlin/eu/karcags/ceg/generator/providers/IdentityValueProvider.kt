package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

/**
 * Identity test value provider. It responses with the source number.
 * @constructor creates a identity test value provider
 * @param testType the test type
 * @param operator the expression operator
 * @param T the type
 */
class IdentityValueProvider<T>(testType: TestType, operator: Operator) : TestValueProvider<T>(testType, operator) {

    override fun get(value: T): T = value
}