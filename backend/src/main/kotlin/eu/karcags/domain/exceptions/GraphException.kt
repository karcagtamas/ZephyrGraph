package eu.karcags.domain.exceptions

open class GraphException(msg: String) : ServerException(msg) {

    class ParseException(msg: String) : GraphException(msg)

    class ValidateException(msg: String) : GraphException(msg)

    class ConvertException(msg: String) : GraphException(msg)
}