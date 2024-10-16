package eu.karcags.ceg.graphmodel.expressions

data class LogicalExpression(val left: Operand, val right: Operand, val operator: Operator) {

    override fun toString(): String {
        return "$left $operator $right"
    }
}