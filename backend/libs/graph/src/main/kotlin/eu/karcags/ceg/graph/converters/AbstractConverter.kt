package eu.karcags.ceg.graph.converters

import eu.karcags.ceg.graph.Graph

abstract class AbstractConverter<T> {

    abstract fun convert(graph: Graph): T
}