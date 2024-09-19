package eu.karcags.ceg.graphmodel.exceptions

open class GraphException(msg: String) : Exception(msg) {
    class ValidateException(msg: String) : GraphException(msg)
}