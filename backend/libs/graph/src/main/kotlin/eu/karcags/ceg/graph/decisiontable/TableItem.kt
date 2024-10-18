package eu.karcags.ceg.graph.decisiontable

import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition

class TableItem(val node: NodeDefinition, val value: TableItemValue?, val isEffect: Boolean = false)