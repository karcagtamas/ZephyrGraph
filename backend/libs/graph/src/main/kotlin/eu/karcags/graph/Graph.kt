package eu.karcags.graph

import eu.karcags.graph.visual.GraphConverter
import eu.karcags.graph.visual.VisualGraph

class Graph(val rules: List<Rule>) {

    fun toVisualGraph(): VisualGraph {
        return GraphConverter().convert(this)
    }
}