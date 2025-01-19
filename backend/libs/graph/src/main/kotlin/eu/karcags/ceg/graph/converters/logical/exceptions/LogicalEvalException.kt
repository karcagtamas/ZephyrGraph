package eu.karcags.ceg.graph.converters.logical.exceptions

import eu.karcags.ceg.graphmodel.exceptions.GraphException

/**
 * Logical evaluation exception.
 * @constructor creates a logical evaluation graph exception.
 * @param msg the exception message
 */
class LogicalEvalException(msg: String) : GraphException(msg)