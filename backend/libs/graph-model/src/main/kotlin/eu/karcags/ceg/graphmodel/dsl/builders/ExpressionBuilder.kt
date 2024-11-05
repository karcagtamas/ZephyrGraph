package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.exceptions.GraphException
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression

class ExpressionBuilder : AbstractBuilder<LogicalExpression>() {
    var expression: LogicalExpression? = null

    override fun build(): LogicalExpression = expression!!

    override fun validate(): Boolean {
        if (expression == null) {
            throw GraphException.ValidateException("Expression must be set in the expression clause")
        }

        val testResult = expression!!.test()

        if (testResult != null) {
            throw GraphException.ValidateException("$testResult in expression clause ($expression)")
        }

        return true
    }
}