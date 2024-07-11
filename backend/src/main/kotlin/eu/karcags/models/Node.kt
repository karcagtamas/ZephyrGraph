package eu.karcags.models

import kotlinx.serialization.Serializable

@Serializable
data class Node(val id: String, val displayName: String, val meta: NodeMeta)

enum class NodeType {
    CAUSE,
    ACTION,
    EFFECT
}

@Serializable
open class NodeMeta(val type: NodeType) {

    class CauseMeta : NodeMeta(NodeType.CAUSE)

    class ActionMeta(val action: Action) : NodeMeta(NodeType.ACTION)

    class EffectMeta : NodeMeta(NodeType.EFFECT)

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

enum class Action {
    AND,
    OR
}