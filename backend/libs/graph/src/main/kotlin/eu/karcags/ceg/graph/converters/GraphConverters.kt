package eu.karcags.ceg.graph.converters

import eu.karcags.ceg.generator.BVA
import eu.karcags.ceg.graph.converters.bva.BVAConverter
import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphConverter
import eu.karcags.ceg.graph.converters.logical.refiners.*
import eu.karcags.ceg.graph.converters.visual.VisualGraph
import eu.karcags.ceg.graph.converters.visual.VisualGraphConverter
import eu.karcags.ceg.graphmodel.Graph

/**
 * Converts graph to visual graph.
 * @return the constructed visual graph
 */
fun Graph.toVisualGraph(): VisualGraph {
    return VisualGraphConverter()
        .convert(this)
}

/**
 * Converts graph to logical graph.
 * @return the constructed logical graph
 */
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

/**
 * Converts graph to stepped logical graph.
 * @return the constructed final logical graph and all steps
 */
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

/**
 * Converts logical graph into BVA.
 * @return list of BVA results
 */
fun LogicalGraph.toBVA(): List<BVA.FinalResult> {
    return BVAConverter()
        .convert(this)
}