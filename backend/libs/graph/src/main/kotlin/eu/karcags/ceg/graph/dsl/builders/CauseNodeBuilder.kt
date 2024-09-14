package eu.karcags.ceg.graph.dsl.builders

import eu.karcags.ceg.graph.models.Definition
import eu.karcags.ceg.graph.models.Node
import eu.karcags.ceg.graph.validators.DefinitionValidator

class CauseNodeBuilder : NodeBuilder<Node.Cause>() {
    override var definition: Definition? = Definition(null, null)

    override fun build(): Node.Cause = Node.Cause(displayName, definition!!, description)

    override fun validate() = DefinitionValidator().validate(definition)
}