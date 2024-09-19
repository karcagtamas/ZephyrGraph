package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.Definition
import eu.karcags.ceg.graphmodel.Node

abstract class NodeBuilder<T> : AbstractBuilder<T>() where T : Node {
    var displayName: String = ""
    open var definition: Definition? = null
    var description: String? = null
}