package eu.karcags.ceg.graph.models

import java.util.UUID

open class Node(val displayName: String, val definition: Definition?, val description: String?) {

    val id = UUID.randomUUID().toString()

    class Cause(displayName: String, definition: Definition, description: String?) : Node(displayName, definition, description)

    class Effect(displayName: String, definition: Definition, description: String?) : Node(displayName, definition, description)

    open class UnaryAction(displayName: String, val inner: Node) : Node(displayName, null, null) {

        class Not(inner: Node) : UnaryAction("NOT", inner)
    }

    open class BinaryAction(displayName: String, val left: Node, val right: Node) : Node(displayName, null, null) {

        class And(left: Node, right: Node) : BinaryAction("AND", left, right)

        class Or(left: Node, right: Node) : BinaryAction("OR", left, right)
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