package eu.karcags.ceg.graph.converters.visual.components

import eu.karcags.ceg.graph.converters.visual.Action
import kotlinx.serialization.Serializable

@Serializable
open class NodeMeta(val type: NodeType, val expression: String?, val description: String?) {

    class CauseMeta(expression: String?, description: String?) : NodeMeta(NodeType.CAUSE, expression, description)

    class ActionMeta(expression: String?, description: String?, val action: Action) : NodeMeta(NodeType.ACTION, expression, description)

    class EffectMeta(expression: String?, description: String?) : NodeMeta(NodeType.EFFECT, expression, description)

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