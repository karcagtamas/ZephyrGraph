package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException
import eu.karcags.ceg.graphmodel.expressions.Variable

class NotNodeBuilder(val nodeProvider: ValueProvider<String, Node.Cause>, val variableProvider: ValueProvider<String, Variable<*>>) : AbstractBuilder<Node.UnaryAction.Not>() {
    var node: Node? = null

    override fun build(): Node.UnaryAction.Not = Node.UnaryAction.Not(node!!)

    override fun validate(): Boolean {
        if (node == null) {
            throw GraphException.ValidateException("Not clause must have an inner node")
        }

        return true
    }
}