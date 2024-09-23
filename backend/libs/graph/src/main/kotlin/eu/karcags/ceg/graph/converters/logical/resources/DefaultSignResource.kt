package eu.karcags.ceg.graph.converters.logical.resources

class DefaultSignResource : AbstractSignResource() {
    companion object {
        private const val NODE = "{0}"
        private const val NOT = "¬{0}"
        private const val IMPLICATE = "({0} → {1})"
        private const val OR = "({0} ∨ {1})"
        private const val AND = "({0} ∧ {1})"
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