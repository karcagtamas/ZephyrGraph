package eu.karcags.ceg.graph.converters

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphConverter
import eu.karcags.ceg.graph.converters.logical.refiners.CNF
import eu.karcags.ceg.graph.converters.logical.refiners.ImplicationEliminator
import eu.karcags.ceg.graph.converters.logical.refiners.NegationInwardMover
import eu.karcags.ceg.graph.converters.visual.VisualGraph
import eu.karcags.ceg.graph.converters.visual.VisualGraphConverter
import eu.karcags.ceg.graphmodel.Graph

fun Graph.toVisualGraph(): VisualGraph {
    return VisualGraphConverter()
        .convert(this)
}

fun Graph.toLogicalGraph(): LogicalGraph {
    return LogicalGraphConverter()
        .addRefiners {
            return@addRefiners listOf(
                ImplicationEliminator(),
                NegationInwardMover(),
                CNF(),
            )
        }
        .convert(this)
}