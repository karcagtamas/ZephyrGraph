package eu.karcags.ceg.graph.converters

import eu.karcags.ceg.graphmodel.Graph

/**
 * Abstract graph converter.
 * @param U the destination type
 */
abstract class AbstractGraphConverter<U> : AbstractConverter<Graph, U>()