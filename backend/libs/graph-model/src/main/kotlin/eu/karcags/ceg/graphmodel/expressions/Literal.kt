package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

data class Literal<T>(val value: T) : Operand {

    override fun toString(): String {
        return "lit($value)"
    }

    override fun getType(): KClass<*> {
        return value!!::class
    }
}