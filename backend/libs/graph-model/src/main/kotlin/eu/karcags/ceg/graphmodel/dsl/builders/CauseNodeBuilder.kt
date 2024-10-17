package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression

class CauseNodeBuilder(val displayName: String) : AbstractBuilder<Node.Cause>() {
    var description: String? = null
    var expression: LogicalExpression? = null

    override fun build(): Node.Cause =
        Node.Cause(displayName, expression!!, description)

    override fun validate(): Boolean {
        if (expression == null) {
            throw GraphException.ValidateException("Cause expression must be set in cause $displayName")
        }

        if (!expression!!.test()) {
            throw GraphException.ValidateException("The type of the provided expression is invalid in cause $displayName (${expression.toString()})")
        }

        return true
    }
}