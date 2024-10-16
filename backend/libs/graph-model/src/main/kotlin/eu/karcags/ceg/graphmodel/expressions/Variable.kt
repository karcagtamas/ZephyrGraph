package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

data class Variable(val name: String) : Operand {

    override fun toString(): String {
        return "var($name)"
    }

    override fun getType(): KClass<*> {
        return Any::class
    }
}