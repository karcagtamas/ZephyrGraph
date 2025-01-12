package eu.karcags.ceg.graph.exceptions

import eu.karcags.ceg.graphmodel.exceptions.GraphException

/**
 * Graph export exception.
 * @constructor creates a graph export exception
 * @param msg the exception message
 */
class GraphExportException(msg: String) : GraphException(msg)