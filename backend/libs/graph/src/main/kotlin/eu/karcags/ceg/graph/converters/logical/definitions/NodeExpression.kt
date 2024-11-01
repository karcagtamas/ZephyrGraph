package eu.karcags.ceg.graph.converters.logical.definitions

import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import eu.karcags.ceg.graphmodel.expressions.Operator
import kotlinx.serialization.Serializable

@Serializable
data class NodeExpression(val operator: Operator) {
    companion object {
        fun of(expression: LogicalExpression): NodeExpression {
            return NodeExpression(expression.operator)
        }

        fun ofNullable(expression: LogicalExpression?): NodeExpression? {
            if (expression == null) return null

            return of(expression)
        }
    }
}