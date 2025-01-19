package eu.karcags.ceg.graphmodel

import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import java.util.UUID

/**
 * Graph node entity.
 * @property displayName the display name of the node
 * @property expression the inner logical expression of the node
 * @property description the optional description of the node
 * @property id the identifier of the node (randomly generated)
 * @param displayName the display name of the node
 * @param expression the inner logical expression of the node
 * @param description the optional description of the node
 * @constructor creates a node definition from an expression
 */
open class Node(val displayName: String, val expression: LogicalExpression?, val description: String?) {

    val id = UUID.randomUUID().toString()

    /**
     * Cause graph node.
     * @constructor creates a cause node definition.
     * @param displayName the display name of the node
     * @param expression the inner logical expression of the node
     * @param description the optional description of the node
     */
    class Cause(displayName: String, expression: LogicalExpression, description: String?) :
        Node(displayName, expression, description)

    /**
     * Effect graph node.
     * @constructor creates an effect node definition.
     * @param displayName the display name of the node
     * @param description the optional description of the node
     */
    class Effect(displayName: String, description: String?) : Node(displayName, null, description)

    /**
     * Unary action graph node. Represents a unary logical action/operator in the graph.
     * @property inner the inner (wrapped) node definition
     * @constructor creates an unary action node definition
     * @param displayName the display name of the node
     */
    open class UnaryAction(displayName: String, val inner: Node) : Node(displayName, null, null) {

        /**
         * Logical NOT action graph node.
         * @constructor creates a NOT action node definition
         * @property inner the inner (wrapped) node definition
         */
        class Not(inner: Node) : UnaryAction("NOT", inner)
    }

    /**
     * Binary action graph node. Represents a binary logical action/operator in the graph.
     * @property nodes the inner (wrapped) node definitions
     * @constructor creates a binary action node definition
     * @param displayName the display name of the node
     */
    open class BinaryAction(displayName: String, val nodes: Set<Node>) : Node(displayName, null, null) {

        /**
         * Logical AND action graph node.
         * @constructor creates a AND action node definition
         * @property nodes the inner (wrapped) node definitions
         */
        class And(nodes: Set<Node>) : BinaryAction("AND", nodes)

        /**
         * Logical OR action graph node.
         * @constructor creates an OR action node definition
         * @property nodes the inner (wrapped) node definitions
         */
        class Or(nodes: Set<Node>) : BinaryAction("OR", nodes)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other is Node) {
            return other.id == this.id
        }

        return false
    }
}