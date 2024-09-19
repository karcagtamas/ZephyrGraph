package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Definition
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.dsl.validators.DefinitionValidator

class EffectNodeBuilder : NodeBuilder<Node.Effect>() {
    override var definition: Definition? = Definition(null, null)

    override fun build(): Node.Effect =
        Node.Effect(displayName, definition!!, description)

    override fun validate(): Boolean = DefinitionValidator().validate(definition)
}