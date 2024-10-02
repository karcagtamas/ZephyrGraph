package eu.karcags.ceg.graph.decisiontable

import eu.karcags.ceg.graph.converters.logical.definitions.NodeDefinition

class TableRow(val node: NodeDefinition, val isEffect: Boolean = false) {

    val items = mutableListOf<TableItem?>()

    fun addItem(item: TableItem?) {
        items.add(if (isEffect && item == TableItem.NotUsed) null else item)
    }
}