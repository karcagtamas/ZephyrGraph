package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.exceptions.GraphException
import eu.karcags.ceg.graphmodel.expressions.Expression

class ExpressionBuilder : AbstractBuilder<Expression>() {
    var expression: Expression? = null

    override fun build(): Expression = expression!!

    override fun validate(): Boolean {
        if (expression == null) {
            throw GraphException.ValidateException("Expression must be set")
        }

        return true
    }
}