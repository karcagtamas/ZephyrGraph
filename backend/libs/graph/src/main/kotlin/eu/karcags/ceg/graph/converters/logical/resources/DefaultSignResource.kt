package eu.karcags.ceg.graph.converters.logical.resources

/**
 * Default sign resource.
 * @constructor creates a default sign resource
 */
class DefaultSignResource : AbstractSignResource() {
    companion object {
        private const val NODE = "{0}"
        private const val NOT = "¬{0}"
        private const val OR = "∨"
        private const val AND = "∧"
    }

    override fun sign(value: Sign, dynamicItems: Int): String {
        return when (value) {
            Sign.Node -> NODE
            Sign.Not -> NOT
            Sign.And -> "(${constructDynamicSign(dynamicItems, " $AND ")})"
            Sign.Or -> "(${constructDynamicSign(dynamicItems, " $OR ")})"
        }
    }
}