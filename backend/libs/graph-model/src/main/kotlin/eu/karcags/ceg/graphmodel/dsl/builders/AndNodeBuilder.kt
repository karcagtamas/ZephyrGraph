package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException
import kotlin.collections.mutableSetOf

class AndNodeBuilder(val graphNodes: Set<Node.Cause>) : AbstractBuilder<Node.BinaryAction.And>() {
    private val nodes = mutableSetOf<Node>()

    override fun build(): Node.BinaryAction.And = Node.BinaryAction.And(nodes)

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