package eu.karcags.ceg.graph.converters.visual

import kotlinx.serialization.Serializable

@Serializable
data class VisualGraph(val edges: List<VisualEdge>, val nodes: List<VisualNode>)

@Serializable
data class VisualEdge(val source: VisualNode, val target: VisualNode) {
    var id: String = "${source.id}-${target.id}"
}

@Serializable
data class VisualNode(val id: String, val displayName: String, val meta: NodeMeta)

enum class NodeType {
    CAUSE,
    ACTION,
    EFFECT
}

enum class Action {
    AND,
    OR,
    NOT
}

@Serializable
open class NodeMeta(val type: NodeType, val definition: VisualDefinition?, val description: String?) {

    class CauseMeta(definition: VisualDefinition?, description: String?) : NodeMeta(NodeType.CAUSE, definition, description)

    class ActionMeta(definition: VisualDefinition?, description: String?, val action: Action) : NodeMeta(NodeType.ACTION, definition, description)

    class EffectMeta(definition: VisualDefinition?, description: String?) : NodeMeta(NodeType.EFFECT, definition, description)

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
data class VisualDefinition(val expression: String?, val statement: String?)