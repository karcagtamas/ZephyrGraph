package eu.karcags.ceg.graphmodel.expressions

import kotlin.reflect.KClass

data class Expression(val left: Operand, val right: Operand, val operator: Operator) : Operand, TypeTestable {

    override fun toString(): String {
        return "$left $operator $right"
    }

    override fun test(): Boolean {
        val leftType = left.getType()
        val rightType = right.getType()

        if (leftType == rightType) {
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

    override fun getType(): KClass<*> {
        val leftType = left.getType()
        val rightType = right.getType()

        if (left !is Variable) {
            return leftType
        }

        return rightType
    }
}