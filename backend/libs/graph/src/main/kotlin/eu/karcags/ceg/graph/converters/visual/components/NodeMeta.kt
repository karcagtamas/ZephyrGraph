package eu.karcags.ceg.graph.converters.visual.components

import eu.karcags.ceg.graph.converters.visual.Action
import kotlinx.serialization.Serializable

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