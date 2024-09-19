package eu.karcags.ceg.graph.converters

import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphConverter
import eu.karcags.ceg.graph.converters.logical.refiners.ApplyDistributiveLaw
import eu.karcags.ceg.graph.converters.logical.refiners.ImplicationElimination
import eu.karcags.ceg.graph.converters.logical.refiners.NegationInwardMover
import eu.karcags.ceg.graph.converters.logical.resources.PremadeResources
import eu.karcags.ceg.graph.converters.visual.VisualGraph
import eu.karcags.ceg.graph.converters.visual.VisualGraphConverter
import eu.karcags.ceg.graphmodel.Graph

fun Graph.toVisualGraph(): VisualGraph {
    return VisualGraphConverter()
        .convert(this)
}

fun Graph.toLogicalGraph(): LogicalGraph {
    return LogicalGraphConverter(PremadeResources.DEFAULT)
        .addRefiners { resource ->
            return@addRefiners listOf(
                ImplicationElimination(resource),
                NegationInwardMover(resource),
                ApplyDistributiveLaw(resource)
            )
        }
        .convert(this)
}