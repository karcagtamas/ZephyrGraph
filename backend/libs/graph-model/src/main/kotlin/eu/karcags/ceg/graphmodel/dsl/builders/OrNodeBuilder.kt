package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException
import eu.karcags.ceg.graphmodel.expressions.Variable

/**
 * OR node ([Node.BinaryAction.Or]) builder.
 * @property nodeProvider node provider store for checks
 * @property variableProvider variable provider store for checks
 * @property nodes the collected nodes for the OR node wrapping
 * @constructor creates an OR node builder
 * @param nodeProvider node provider store for checks
 * @param variableProvider variable provider store for checks
 */
class OrNodeBuilder(val nodeProvider: ValueProvider<String, Node.Cause>, val variableProvider: ValueProvider<String, Variable<*>>) : AbstractBuilder<Node.BinaryAction.Or>() {
    private val nodes = mutableSetOf<Node>()

    override fun build(): Node.BinaryAction.Or = Node.BinaryAction.Or(nodes)

    override fun validate(): Boolean {
        if (nodes.size < 2) {
            throw GraphException.ValidateException("The number of nodes in the or clause must be at least 2 (currently ${nodes.size})")
        }

        return true
    }

    /**
     * Adds node to the [nodes] collections.
     * @param node the node for insert
     */
    fun addNode(node: Node) {
        nodes.add(node)
    }
}