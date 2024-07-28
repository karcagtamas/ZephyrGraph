package eu.karcags.graph.converters.visual

import kotlinx.serialization.Serializable

@Serializable
data class VisualGraph(val edges: List<Edge>, val nodes: List<Node>) {
}

@Serializable
data class Edge(val source: Node, val target: Node) {
    var id: String = "${source.id}-${target.id}"
}

@Serializable
data class Node(val id: String, val displayName: String, val meta: NodeMeta)

enum class NodeType {
    CAUSE,
    ACTION,
    EFFECT
}

@Serializable
open class NodeMeta(val type: NodeType, val definition: Definition?, val description: String?) {

    class CauseMeta(definition: Definition?, description: String?) : NodeMeta(NodeType.CAUSE, definition, description)

    class ActionMeta(definition: Definition?, description: String?, val action: Action) : NodeMeta(NodeType.ACTION, definition, description)

    class EffectMeta(definition: Definition?, description: String?) : NodeMeta(NodeType.EFFECT, definition, description)

    override fun equals(other: Any?): Boolean {
        if (other !is NodeMeta) {
            return false
        }

        return type == other.type && javaClass == other.javaClass
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }
}

@Serializable
data class Definition(val expression: String?, val statement: String?)

enum class Action {
    AND,
    OR,
    NOT
}