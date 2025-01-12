package eu.karcags.ceg.graph.converters.logical.resources

/**
 * Text sign resource.
 * @constructor creates a text sign resource
 */
class TextSignResource : AbstractSignResource() {
    companion object {
        private const val NODE = "{0}"
        private const val NOT = "not {0}"
        private const val OR = "or"
        private const val AND = "and"
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