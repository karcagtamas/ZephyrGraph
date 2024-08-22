package eu.karcags.ceg.graph

import eu.karcags.ceg.graph.converters.visual.VisualGraphConverter
import eu.karcags.ceg.graph.converters.visual.VisualGraph

class Graph(val rules: List<Rule>) {

    fun toVisualGraph(): VisualGraph {
        return VisualGraphConverter().convert(this)
    }
}