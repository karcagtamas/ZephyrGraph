package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException
import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import eu.karcags.ceg.graphmodel.expressions.Variable

/**
 * Cause node ([Node.Cause]) builder.
 * @property displayName the display name of the target node
 * @property variableProvider variable provider store for checks
 * @property description the description of the target node
 * @property expression the inner expression of the target node
 * @constructor creates a cause node builder
 * @param displayName the display name of the target cause node
 * @param variableProvider variable provider store for checks
 */
class CauseNodeBuilder(val displayName: String, val variableProvider: ValueProvider<String, Variable<*>>) : AbstractBuilder<Node.Cause>() {
    var description: String? = null
    var expression: LogicalExpression? = null

    override fun build(): Node.Cause =
        Node.Cause(displayName, expression!!, description)

    override fun validate(): Boolean {
        if (expression == null) {
            throw GraphException.ValidateException("Cause expression must be set in cause $displayName")
        }

        val testResult = expression!!.test()

        if (testResult != null) {
            throw GraphException.ValidateException("$testResult in cause $displayName ($expression)")
        }

        return true
    }
}