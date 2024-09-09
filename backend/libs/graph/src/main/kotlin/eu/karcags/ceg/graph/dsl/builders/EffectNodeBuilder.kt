package eu.karcags.ceg.graph.dsl.builders

import eu.karcags.ceg.graph.Definition
import eu.karcags.ceg.graph.Node
import eu.karcags.ceg.graph.validators.DefinitionValidator

class EffectNodeBuilder : NodeBuilder<Node.EffectNode>() {
    override var definition: Definition? = Definition(null, null)

    override fun build(): Node.EffectNode = Node.EffectNode(displayName, definition!!, description)

    override fun validate(): Boolean = DefinitionValidator().validate(definition)
}