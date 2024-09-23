package eu.karcags.ceg.graph.converters.logical.resources

class TextSignResource : AbstractSignResource() {
    companion object {
        private const val NODE = "{0}"
        private const val NOT = "not {0}"
        private const val IMPLICATE = "({0} -> {1})"
        private const val OR = "({0} and {1})"
        private const val AND = "({0} or {1})"
    }

    override fun sign(value: Sign): String {
        return when(value) {
            Sign.Node -> NODE
            Sign.Not -> NOT
            Sign.Implicate -> IMPLICATE
            Sign.And -> AND
            Sign.Or -> OR
        }
    }
}