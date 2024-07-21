package eu.karcags.graph

import eu.karcags.graph.converters.visual.VisualGraphConverter
import eu.karcags.graph.converters.visual.VisualGraph

class Graph(val rules: List<Rule>) {

    fun toVisualGraph(): VisualGraph {
        return VisualGraphConverter().convert(this)
    }
}