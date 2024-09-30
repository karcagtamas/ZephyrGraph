package eu.karcags.ceg.graphmodel

import java.util.UUID

open class Node(val displayName: String, val expression: String?, val description: String?) {

    val id = UUID.randomUUID().toString()

    class Cause(displayName: String, expression: String, description: String?) : Node(displayName, expression, description)

    class Effect(displayName: String, expression: String, description: String?) : Node(displayName, expression, description)

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