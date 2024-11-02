package eu.karcags.ceg.graphmodel.expressions

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
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