package eu.karcags.graph.dsl.builders

import eu.karcags.graph.Definition
import eu.karcags.graph.Node
import eu.karcags.graph.validators.DefinitionValidator

class CauseNodeBuilder : NodeBuilder<Node.CauseNode>() {
    override var definition: Definition? = Definition(null, null)

    override fun build(): Node.CauseNode = Node.CauseNode(displayName, definition!!, description)

    override fun validate() = DefinitionValidator().validate(definition)
}