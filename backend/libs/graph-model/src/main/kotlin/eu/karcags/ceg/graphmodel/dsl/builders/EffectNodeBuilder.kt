package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException

/**
 * Effect node ([Node.Effect]) builder.
 * @property id the identifier of the effect
 * @property description the description of the target node
 * @constructor creates an effect node builder
 * @param id the identifier of the effect
 */
class EffectNodeBuilder(val id: Int) : AbstractBuilder<Node.Effect>() {
    var description: String = ""

    override fun build(): Node.Effect =
        Node.Effect("E$id", description)

    override fun validate(): Boolean {
        if (description.isEmpty()) {
            throw GraphException.ValidateException("Effect (E$id) description string cannot be empty in the effect clause")
        }

        return true
    }
}