package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

data class Literal<T>(val value: T) : Operand {

    override fun toString(): String = "lit($value)"

    override fun getType(): KClass<*> = value!!::class

    override fun isVariable(): Boolean = false

    override fun simplified(): Operand = this
}