package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Definition
import eu.karcags.graph.Node
import eu.karcags.graph.validators.DefinitionValidator

class EffectNodeBuilder : NodeBuilder<Node.EffectNode>() {
    override var definition: Definition? = Definition(null, null)

    override fun build(): Node.EffectNode = Node.EffectNode(displayName, definition!!, description)

    override fun validate(): Boolean = DefinitionValidator().validate(definition)
}