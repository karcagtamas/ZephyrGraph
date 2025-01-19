package eu.karcags.ceg.graphmodel.expressions

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

/**
 * Logical expression definition.
 * @property left the left operand
 * @property right the right operand
 * @property operator the expression operator
 * @constructor creates a logical expression from the [left] and [right] operands and the [operator]
 * @param left the left operand
 * @param right the right operand
 * @param operator the expression operator
 */
@Serializable
data class LogicalExpression(val left: Operand, val right: Operand, val operator: Operator) : TypeTestable,
    Invertible<LogicalExpression> {

    /**
     * Reorders the logical expression operands. It tries to set the variable operands on the left side of the expression.
     * @return the reordered expression (if the left is already a variable, it won't change the object)
     */
    fun ordered(): LogicalExpression {
        if (left.isVariable() || !right.isVariable()) {
            return this
        }

        return LogicalExpression(right, left, operator.symmetry())
    }

    /**
     * Simplifies the logical expression. The [left] and [right] operands will be simplified and the expression will be re-constructed.
     * @return the simplified expression
     */
    fun simplified(): LogicalExpression {
        return LogicalExpression(left.simplified(), right.simplified(), operator)
    }

    /**
     * Gets the variable operand from the expression.
     * @return the variable from the [left] or the [right] operand
     */
    fun getVariable(): Variable<*>? {
        return findVariable(left) ?: findVariable(right)
    }

    override fun invert(): LogicalExpression {
        return LogicalExpression(left, right, operator.invert())
    }

    override fun toString(): String {
        return "$left $operator $right"
    }

    override fun test(): String? {
        if (Operator.logicalOperators().none { it == operator }) {
            return "The operator is not logical"
        }

        val types = getTypes()

        if (left is Variable<*> && right is Variable<*>) {
            return "Cannot be each operands are variables"
        }

        if (left is Literal<*> && right is Literal<*>) {
            return "Cannot be each operands are literals"
        }

        if (left is Expression) {
            val res = left.test()
            if (res != null) {
                return "Invalid expression type ($left): $res"
            }
        }

        if (right is Expression) {
            val res = right.test()
            if (res != null) {
                return "Invalid expression type ($right): $res"
            }
        }

        if (types.first != types.second) {
            return "The types of the operands must be the same"
        }

        return null
    }

    private fun getTypes(): Pair<KClass<*>, KClass<*>> {
        return Pair(left.getType(), right.getType())
    }

    private fun findVariable(operand: Operand): Variable<*>? {
        if (operand.isVariable()) {
            if (operand is Variable<*>) {
                return operand
            }

            if (operand is Expression) {
                return findVariable(operand.left) ?: findVariable(operand.right)
            }
        }

        return null
    }
}