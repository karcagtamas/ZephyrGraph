package eu.karcags.ceg.graph.exceptions

import eu.karcags.ceg.graphmodel.exceptions.GraphException

/**
 * Graph parse exception.
 * @constructor creates a graph parse exception
 * @param msg the exception message
 */
class GraphParseException(msg: String) : GraphException(msg)