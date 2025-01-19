package eu.karcags.ceg.graphmodel.expressions

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

/**
 * Logical expression operand.
 */
@Serializable
sealed interface Operand {

    /**
     * Gets the type of the operand.
     * @return Kotlin type of the operand inner value.
     */
    fun getType(): KClass<*>

    /**
     * Determines that the operand is representing a variable or not.
     * @return True if this operand is variable
     */
    fun isVariable(): Boolean

    /**
     * Simplifies the operand object structure. Eliminates the inconsistent or unnecessary wrappings.
     * @return the simplified operand
     */
    fun simplified(): Operand
}