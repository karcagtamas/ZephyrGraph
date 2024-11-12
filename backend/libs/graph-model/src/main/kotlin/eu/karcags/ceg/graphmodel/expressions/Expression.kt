package eu.karcags.ceg.graphmodel.expressions

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
data class Expression(val left: Operand, val right: Operand, val operator: Operator) : Operand, TypeTestable {

    override fun toString(): String {
        return "$left $operator $right"
    }

    override fun test(): String? {
        val leftType = left.getType()
        val rightType = right.getType()

        if (left is Variable<*> && right is Variable<*>) {
            return "Cannot be each operands are variables"
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

        if (leftType != rightType) {
            return "The types of the operands must be the same"
        }

        return null
    }

    override fun getType(): KClass<*> {
        val leftType = left.getType()
        val rightType = right.getType()

        if (left == Any::class) {
            return rightType
        }

        return leftType
    }

    override fun isVariable(): Boolean {
        return left.isVariable() || right.isVariable()
    }

    override fun simplified(): Operand {
        if (test() != null) {
            throw RuntimeException("Invalid expression types")
        }

        if (left is Variable<*> || right is Variable<*>) {
            return this
        }

        val l = left.simplified()
        val r = right.simplified()

        if (l is Literal<*> && r is Literal<*>) {
            if (l.isIntLiteral() && r.isIntLiteral()) {
                val leftValue = l.value as Int
                val rightValue = r.value as Int
                return when (operator) {
                    is Operator.Plus -> Literal(leftValue + rightValue)
                    is Operator.Minus -> Literal(leftValue - rightValue)
                    is Operator.Times -> Literal(leftValue * rightValue)
                    is Operator.Division -> Literal(leftValue / rightValue)
                    else -> throw RuntimeException("Invalid int operator $operator for expression $this")
                }
            }

            if (l.isFloatLiteral() && r.isFloatLiteral()) {
                val leftValue = l.value as Float
                val rightValue = r.value as Float
                return when (operator) {
                    is Operator.Plus -> Literal(leftValue + rightValue)
                    is Operator.Minus -> Literal(leftValue - rightValue)
                    is Operator.Times -> Literal(leftValue * rightValue)
                    is Operator.Division -> Literal(leftValue / rightValue)
                    else -> throw RuntimeException("Invalid double operator $operator for expression $this")
                }
            }

            if (l.isBooleanLiteral() && r.isBooleanLiteral()) {
                val leftValue = l.value as Boolean
                val rightValue = r.value as Boolean
                return when (operator) {
                    is Operator.Plus -> Literal(leftValue || rightValue)
                    is Operator.Times -> Literal(leftValue && rightValue)
                    else -> throw RuntimeException("Invalid boolean operator $operator for expression $this")
                }
            }

            if (l.isStringLiteral() && r.isStringLiteral()) {
                val leftValue = l.value as String
                val rightValue = r.value as String
                return when (operator) {
                    is Operator.Plus -> Literal(leftValue + rightValue)
                    else -> throw RuntimeException("Invalid string operator $operator for expression $this")
                }
            }

            throw RuntimeException("Invalid literal pairs for expression $this")
        }

        return this
    }

    private fun Literal<*>.isIntLiteral(): Boolean {
        return this.getType() == Int::class
    }

    private fun Literal<*>.isFloatLiteral(): Boolean {
        return this.getType() == Float::class
    }

    private fun Literal<*>.isStringLiteral(): Boolean {
        return this.getType() == String::class
    }

    private fun Literal<*>.isBooleanLiteral(): Boolean {
        return this.getType() == Boolean::class
    }
}