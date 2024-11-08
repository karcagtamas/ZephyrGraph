package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

data class Variable<T : Any>(val name: String, val baseType: KClass<T>, val precision: Float = 1f) : Operand {

    override fun toString(): String {
        return "var($name)"
    }

    override fun getType(): KClass<*> {
        return baseType
    }

    override fun isVariable(): Boolean = true

    override fun simplified(): Operand = this
}