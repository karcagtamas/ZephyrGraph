package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException

class EffectNodeBuilder(val id: Int) : AbstractBuilder<Node.Effect>() {
    var description: String? = null
    var expression: String = ""

    override fun build(): Node.Effect =
        Node.Effect("E$id", expression, description)

    override fun validate(): Boolean {
        if (expression.isEmpty()) {
            throw GraphException.ValidateException("Expression cannot be empty")
        }

        return true
    }
}