package eu.karcags.ceg.graph.gpt

import eu.karcags.ceg.graph.exceptions.GraphExportException
import eu.karcags.ceg.graphmodel.expressions.Variable
import kotlin.reflect.KClass

/**
 * Represents a GPT variable based on the [Variable] entity.
 * @property variable the source variable
 * @constructor creates a GPT variable
 * @param variable the source variable
 */
class GPTVariable(val variable: Variable<*>) {

    override fun toString(): String {
        val type = determineVariableType(variable.getType())
        if (type == Type.NUMBER) {
            return "${variable.name}(${type.displayName},${variable.precision})"
        }

        return "${variable.name}(${type.displayName})"
    }

    private fun determineVariableType(type: KClass<*>): Type {
        return when (type) {
            Int::class -> Type.INTEGER
            Boolean::class -> Type.BOOLEAN
            Float::class -> Type.NUMBER
            else -> throw GraphExportException("Unknown variable type: $type")
        }
    }

    /**
     * GPT variable type enum.
     * @property displayName the display name of the type
     */
    enum class Type(val displayName: String) {
        BOOLEAN("bool"),
        INTEGER("int"),
        NUMBER("num");
    }
}