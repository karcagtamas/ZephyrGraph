package eu.karcags.ceg.graphmodel.expressions

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
sealed interface Operand {

    fun getType(): KClass<*>

    fun isVariable(): Boolean

    fun simplified(): Operand
}