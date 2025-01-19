package eu.karcags.ceg.graphmodel.exceptions

/**
 * Graph exception.
 * @constructor creates a graph exception
 * @param msg the exception message
 */
open class GraphException(msg: String) : Exception(msg) {

    /**
     * Graph validate exception.
     * @constructor creates a graph validate exception
     * @param msg the exception message
     */
    class ValidateException(msg: String) : GraphException(msg)
}