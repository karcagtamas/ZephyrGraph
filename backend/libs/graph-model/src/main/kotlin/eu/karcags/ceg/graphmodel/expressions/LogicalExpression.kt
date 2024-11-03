package eu.karcags.ceg.graphmodel.expressions

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
data class LogicalExpression(val left: Operand, val right: Operand, val operator: Operator) : TypeTestable,
    Inversable<LogicalExpression> {

    fun ordered(): LogicalExpression {
        if (left.isVariable() || !right.isVariable()) {
            return this
        }

        return LogicalExpression(right, left, operator.symmetry())
    }

    fun simplified(): LogicalExpression {
        return LogicalExpression(left.simplified(), right.simplified(), operator)
    }

    fun getVariable(): Variable? {
        return findVariable(left) ?: findVariable(right)
    }

    override fun inverse(): LogicalExpression {
        return LogicalExpression(left, right, operator.inverse())
    }

    override fun toString(): String {
        return "$left $operator $right"
    }

    override fun test(): Boolean {
        if (Operator.logicalOperators().none { it == operator }) {
            return false
        }

        val types = getTypes()

        if (types.first == types.second) {
            return true
        }

        if (left is Variable && right !is Variable) {
            return true
        }

        if (left !is Variable && right is Variable) {
            return true
        }

        return false
    }

    private fun getTypes(): Pair<KClass<*>, KClass<*>> {
        return Pair(left.getType(), right.getType())
    }

    private fun findVariable(operand: Operand): Variable? {
        if (operand.isVariable()) {
            if (operand is Variable) {
                return operand
            }

            if (operand is Expression) {
                return findVariable(operand.left) ?: findVariable(operand.right)
            }
        }

        return null
    }
}