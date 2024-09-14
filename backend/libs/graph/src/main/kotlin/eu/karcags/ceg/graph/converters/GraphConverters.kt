package eu.karcags.ceg.graph.converters

import eu.karcags.ceg.graph.Graph
import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphConverter
import eu.karcags.ceg.graph.converters.logical.resources.Resources
import eu.karcags.ceg.graph.converters.visual.VisualGraph
import eu.karcags.ceg.graph.converters.visual.VisualGraphConverter

fun Graph.toVisualGraph(): VisualGraph {
    return VisualGraphConverter().convert(this)
}

fun Graph.toLogicalGraph(): LogicalGraph {
    return LogicalGraphConverter(Resources.DEFAULT).convert(this)
}