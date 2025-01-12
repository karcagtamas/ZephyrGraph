package eu.karcags.ceg.graph.decisiontable

import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition

/**
 * Represents an item from the decision table.
 * @property node the connected node definition
 * @property value the value of the cell
 * @property isEffect the connected node is and effect or not
 * @constructor creates a table item
 * @param node the connected node definition
 * @param value the value of the cell
 * @param isEffect the connected node is and effect or not
 */
class TableItem(val node: NodeDefinition, val value: TableItemValue?, val isEffect: Boolean = false)