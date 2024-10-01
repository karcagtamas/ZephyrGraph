package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException
import eu.karcags.ceg.graphmodel.expressions.Expression

class CauseNodeBuilder(val displayName: String) : AbstractBuilder<Node.Cause>() {
    var description: String? = null
    var expression: Expression? = null

    override fun build(): Node.Cause =
        Node.Cause(displayName, expression!!, description)

    override fun validate(): Boolean {
        if (expression == null) {
            throw GraphException.ValidateException("Expression must be set.")
        }

        return true
    }
}