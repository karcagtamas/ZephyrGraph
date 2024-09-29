package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException

class CauseNodeBuilder(val displayName: String) : AbstractBuilder<Node.Cause>() {
    var description: String? = null
    var expression: String = ""

    override fun build(): Node.Cause =
        Node.Cause(displayName, expression, description)

    override fun validate(): Boolean {
        if (expression.isEmpty()) {
            throw GraphException.ValidateException("Expression cannot be empty")
        }

        return true
    }
}