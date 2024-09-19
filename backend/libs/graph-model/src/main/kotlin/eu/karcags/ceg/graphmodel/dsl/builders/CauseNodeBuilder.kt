package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Definition
import eu.karcags.ceg.graphmodel.Node
import eu.karcags.ceg.graphmodel.dsl.validators.DefinitionValidator

class CauseNodeBuilder : NodeBuilder<Node.Cause>() {
    override var definition: Definition? = Definition(null, null)

    override fun build(): Node.Cause =
        Node.Cause(displayName, definition!!, description)

    override fun validate() = DefinitionValidator().validate(definition)
}