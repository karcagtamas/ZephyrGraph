package eu.karcags.ceg.graphmodel.expressions

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
data class LogicalExpression(val left: Operand, val right: Operand, val operator: Operator) : TypeTestable, Inversable<LogicalExpression> {

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
}