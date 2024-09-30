package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException

class OrNodeBuilder(val graphNodes: Set<Node.Cause>) : AbstractBuilder<Node.BinaryAction.Or>() {
    private val nodes = mutableSetOf<Node>()

    override fun build(): Node.BinaryAction.Or = Node.BinaryAction.Or(nodes)

    override fun validate(): Boolean {
        if (nodes.size < 2) {
            throw GraphException.ValidateException("The number of nodes should be at least 2")
        }

        return true
    }

    fun addNode(node: Node) {
        nodes.add(node)
    }
}