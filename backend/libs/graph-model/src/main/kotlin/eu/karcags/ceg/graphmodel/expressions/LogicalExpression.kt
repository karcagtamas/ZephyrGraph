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

    fun getVariable(): Variable<*>? {
        return findVariable(left) ?: findVariable(right)
    }

    override fun inverse(): LogicalExpression {
        return LogicalExpression(left, right, operator.inverse())
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