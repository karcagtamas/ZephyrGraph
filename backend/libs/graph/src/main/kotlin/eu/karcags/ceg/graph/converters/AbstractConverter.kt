package eu.karcags.ceg.graph.converters

import eu.karcags.ceg.graph.models.Graph

abstract class AbstractConverter<T> {

    abstract fun convert(graph: Graph): T
}