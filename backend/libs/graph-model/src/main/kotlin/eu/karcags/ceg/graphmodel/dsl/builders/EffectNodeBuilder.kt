package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.exceptions.GraphException

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