package eu.karcags.ceg.graph.dsl.builders

import eu.karcags.ceg.graph.models.Definition
import eu.karcags.ceg.graph.models.Node
import eu.karcags.ceg.graph.validators.DefinitionValidator

class EffectNodeBuilder : NodeBuilder<Node.Effect>() {
    override var definition: Definition? = Definition(null, null)

    override fun build(): Node.Effect = Node.Effect(displayName, definition!!, description)

    override fun validate(): Boolean = DefinitionValidator().validate(definition)
}