package eu.karcags.ceg.graph.converters

import eu.karcags.ceg.graphmodel.Graph

abstract class AbstractConverter<T> {

    abstract fun convert(graph: Graph): T
}