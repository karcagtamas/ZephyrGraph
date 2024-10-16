package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

data class LogicalExpression(val left: Operand, val right: Operand, val operator: Operator) : TypeTestable {

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