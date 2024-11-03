package eu.karcags.ceg.generator.providers

import eu.karcags.ceg.generator.TestType
import eu.karcags.ceg.graphmodel.expressions.Operator

abstract class TestValueProvider<T>(val testType: TestType, val operator: Operator) {

    abstract fun get(value: T): T
}