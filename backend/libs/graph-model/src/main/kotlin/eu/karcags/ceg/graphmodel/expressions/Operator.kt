package eu.karcags.ceg.graphmodel.expressions

sealed class Operator {
    object Equal : Operator()
    object NotEqual : Operator()
    object LessThan : Operator()
    object GreaterThan : Operator()
    object LessThanOrEqual : Operator()
    object GreaterThanOrEqual : Operator()
    object Plus : Operator()
    object Minus : Operator()
    object Times : Operator()
    object Division : Operator()

    companion object {
        fun logicalOperators(): List<Operator> {
            return listOf(Equal, NotEqual, LessThan, GreaterThan, LessThanOrEqual, GreaterThanOrEqual)
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
            is Plus -> "+"
            is Minus -> "-"
            is Times -> "*"
            is Division -> "/"
        }
    }
}