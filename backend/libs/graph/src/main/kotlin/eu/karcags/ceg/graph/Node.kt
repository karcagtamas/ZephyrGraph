package eu.karcags.ceg.graph

import java.util.UUID

open class Node(val displayName: String, val definition: Definition?, val description: String?) {

    val id = UUID.randomUUID().toString()

    class CauseNode(displayName: String, definition: Definition, description: String?) : Node(displayName, definition, description)

    class EffectNode(displayName: String, definition: Definition, description: String?) : Node(displayName, definition, description)

    open class UnActionNode(displayName: String, val inner: Node) : Node(displayName, null, null) {

        class NotNode(inner: Node) : UnActionNode("NOT", inner)
    }

    open class BiActionNode(displayName: String, val left: Node, val right: Node) : Node(displayName, null, null) {

        class AndNode(left: Node, right: Node) : BiActionNode("AND", left, right)

        class OrNode(left: Node, right: Node) : BiActionNode("OR", left, right)
    }
}