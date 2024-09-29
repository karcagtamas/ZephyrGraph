package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.exceptions.GraphException

class ExpressionBuilder : AbstractBuilder<String>() {
    var expression: String = ""

    override fun build(): String = expression

    override fun validate(): Boolean {
        if (expression.isEmpty()) {
            throw GraphException.ValidateException("Expression cannot be empty")
        }

        return true
    }
}