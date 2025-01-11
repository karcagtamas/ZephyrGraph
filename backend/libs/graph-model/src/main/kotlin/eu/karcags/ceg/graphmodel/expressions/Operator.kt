package eu.karcags.ceg.graphmodel.expressions

import kotlinx.serialization.Serializable

/**
 * Basic logical and numeric operators.
 */
@Serializable
sealed class Operator {
    object Equal : Operator()
    object NotEqual : Operator()
    object LessThan : Operator()
    object GreaterThan : Operator()
    object LessThanOrEqual : Operator()
    object GreaterThanOrEqual : Operator()
    object InInterval : Operator()
    object NotInInterval : Operator()
    object IsTrue : Operator()
    object IsFalse : Operator()
    object Plus : Operator()
    object Minus : Operator()
    object Times : Operator()
    object Division : Operator()

    companion object {
        /**
         * Gets all the logical operators.
         * @return list of all logical operators
         */
        fun logicalOperators(): List<Operator> {
            return listOf(Equal, NotEqual, LessThan, GreaterThan, LessThanOrEqual, GreaterThanOrEqual, InInterval, NotInInterval, IsTrue, IsFalse)
        }
    }

    /**
     * Gets the invert of the operator.
     * @return the invert pair operator
     */
    fun invert(): Operator {
        return when (this) {
            is Equal -> NotEqual
            is NotEqual -> Equal
            is LessThan -> GreaterThanOrEqual
            is LessThanOrEqual -> GreaterThan
            is GreaterThan -> LessThanOrEqual
            is GreaterThanOrEqual -> LessThan
            is InInterval -> NotInInterval
            is NotInInterval -> InInterval
            is IsTrue -> IsFalse
            is IsFalse -> IsTrue
            is Plus -> Minus
            is Minus -> Plus
            is Times -> Division
            is Division -> Times
        }
    }

    /**
     * Gets the symmetry operator for the operator.
     * @return the symmetry pair operator
     */
    fun symmetry(): Operator {
        return when (this) {
            is LessThan -> GreaterThan
            is LessThanOrEqual -> GreaterThanOrEqual
            is GreaterThan -> LessThan
            is GreaterThanOrEqual -> LessThanOrEqual
            else -> this
        }
    }

    override fun toString(): String {
        return when (this) {
            is Equal -> "=="
            is NotEqual -> "!="
            is LessThan -> "<"
            is GreaterThan -> ">"
            is LessThanOrEqual -> "<="
            is GreaterThanOrEqual -> ">="
            is InInterval -> "in"
            is NotInInterval -> "not in"
            is IsTrue -> "is"
            is IsFalse -> "is"
            is Plus -> "+"
            is Minus -> "-"
            is Times -> "*"
            is Division -> "/"
        }
    }
}