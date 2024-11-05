package eu.karcags.ceg.graph.converters

import eu.karcags.ceg.generator.BVA
import eu.karcags.ceg.graph.converters.bva.BVAConverter
import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphConverter
import eu.karcags.ceg.graph.converters.logical.refiners.*
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
            listOf(
                NegationInwardMover(),
                BinaryCollapser(),
                OppositionEliminator(),
                CNF(),
            )
        }
        .convert(this)
}

fun Graph.toSteppedLogicalGraph(): LogicalGraphConverter.SteppedLogicalGraph {
    return LogicalGraphConverter()
        .addRefiners {
            listOf(
                NegationInwardMover(),
                BinaryCollapser(),
                OppositionEliminator(),
                DNF(),
            )
        }
        .convertToStepped(this)
}

fun LogicalGraph.toBVA(): List<BVA.FinalResult> {
    return BVAConverter()
        .convert(this)
}