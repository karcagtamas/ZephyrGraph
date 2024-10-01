package eu.karcags.ceg.graphmodel.expressions

sealed class Operator {
    object Equal : Operator()
    object NotEqual : Operator()
    object LessThan : Operator()
    object GreaterThan : Operator()
    object LessThanOrEqual : Operator()
    object GreaterThanOrEqual : Operator()

    override fun toString(): String {
        return when (this) {
            is Equal -> "=="
            is NotEqual -> "!="
            is LessThan -> "<"
            is GreaterThan -> ">"
            is LessThanOrEqual -> "<="
            is GreaterThanOrEqual -> ">="
        }
    }
}