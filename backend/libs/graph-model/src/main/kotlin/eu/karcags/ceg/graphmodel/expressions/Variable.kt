package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

/**
 * Variable expression.
 * @property name the name of the variable
 * @property baseType the type of the variable content (value) - it refers to the [T] type parameter
 * @property precision the variable precision
 * @constructor creates a variable operand expression
 * @param name the name of the variable
 * @param baseType the type of the variable content (value) - it refers to the [T] type parameter
 * @param precision the variable precision
 * @param T the type of the variable content
 */
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