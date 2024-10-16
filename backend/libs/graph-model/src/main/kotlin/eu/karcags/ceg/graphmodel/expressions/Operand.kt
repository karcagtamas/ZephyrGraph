package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

interface Operand {

    fun getType(): KClass<*>
}