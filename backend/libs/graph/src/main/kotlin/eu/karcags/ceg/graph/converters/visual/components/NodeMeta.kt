package eu.karcags.ceg.graph.converters.visual.components

import eu.karcags.ceg.graph.converters.visual.Action
import kotlinx.serialization.Serializable

/**
 * Represents metadata for the node definitions.
 * @property type the type of the node
 * @property expression the optional expression of the node
 * @property description the optional description of the node
 * @constructor creates a node meta object
 * @param type the type of the node
 * @param expression the optional expression of the node
 * @param description the optional description of the node
 */
@Serializable
open class NodeMeta(val type: NodeType, val expression: String?, val description: String?) {

    /**
     * Cause metadata representation.
     * @constructor creates a cause metadata object
     * @param expression the optional expression of the node
     * @param description the optional description of the node
     */
    class CauseMeta(expression: String?, description: String?) : NodeMeta(NodeType.CAUSE, expression, description)

    /**
     * Action metadata representation.
     * @property action the type of the action
     * @constructor creates a action metadata object
     * @param expression the optional expression of the node
     * @param description the optional description of the node
     */
    class ActionMeta(expression: String?, description: String?, val action: Action) : NodeMeta(NodeType.ACTION, expression, description)

    /**
     * Effect metadata representation.
     * @constructor creates an effect metadata object
     * @param expression the optional expression of the node
     * @param description the optional description of the node
     */
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