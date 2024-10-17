package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException

class NotNodeBuilder(val graphNodes: Set<Node.Cause>) : AbstractBuilder<Node.UnaryAction.Not>() {
    var node: Node? = null

    override fun build(): Node.UnaryAction.Not = Node.UnaryAction.Not(node!!)

    override fun validate(): Boolean {
        if (node == null) {
            throw GraphException.ValidateException("Not clause must have an inner node")
        }

        return true
    }
}