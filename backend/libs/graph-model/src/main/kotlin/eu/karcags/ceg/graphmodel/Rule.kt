package eu.karcags.ceg.graphmodel

/**
 * Graph rule.
 * @property id the identifier of the rule
 * @property cause cause definition
 * @property effect effect definition
 * @property displayName the display name of the rule
 * @constructor creates a rule definition.
 */
data class Rule(val id: Int, val cause: Node, val effect: Node.Effect) {
    val displayName = "R$id"
}