package eu.karcags.ceg.graph.dsl.builders

import eu.karcags.ceg.graph.Definition
import eu.karcags.ceg.graph.Node
import eu.karcags.ceg.graph.validators.DefinitionValidator

class CauseNodeBuilder : NodeBuilder<Node.CauseNode>() {
    override var definition: Definition? = Definition(null, null)

    override fun build(): Node.CauseNode = Node.CauseNode(displayName, definition!!, description)

    override fun validate() = DefinitionValidator().validate(definition)
}