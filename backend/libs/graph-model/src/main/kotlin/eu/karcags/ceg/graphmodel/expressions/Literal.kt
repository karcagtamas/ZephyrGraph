package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

/**
 * Literal operand. It stores an inner Kotlin value.
 * @property value the inner value
 * @constructor creates a literal operand
 * @param value the inner value
 * @param T the type of the inner value
 */
data class Literal<T>(val value: T) : Operand {

    override fun toString(): String = "lit($value)"

    override fun getType(): KClass<*> = value!!::class

    override fun isVariable(): Boolean = false

    override fun simplified(): Operand = this
}