package eu.karcags.ceg.graphmodel

import eu.karcags.ceg.graphmodel.expressions.LogicalExpression
import java.util.UUID

open class Node(val displayName: String, val expression: LogicalExpression?, val description: String?) {

    val id = UUID.randomUUID().toString()

    class Cause(displayName: String, expression: LogicalExpression, description: String?) :
        Node(displayName, expression, description)

    class Effect(displayName: String, description: String?) : Node(displayName, null, description)

    open class UnaryAction(displayName: String, val inner: Node) : Node(displayName, null, null) {

        class Not(inner: Node) : UnaryAction("NOT", inner)
    }

    open class BinaryAction(displayName: String, val nodes: Set<Node>) : Node(displayName, null, null) {

        class And(nodes: Set<Node>) : BinaryAction("AND", nodes)

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