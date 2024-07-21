package eu.karcags.graph.converters

import eu.karcags.graph.Graph

abstract class AbstractConverter<T> {

    abstract fun convert(graph: Graph): T
}