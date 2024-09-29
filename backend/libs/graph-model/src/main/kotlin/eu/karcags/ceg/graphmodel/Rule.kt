package eu.karcags.ceg.graphmodel

data class Rule(val id: Int, val cause: Node, val effect: Node.Effect) {
    val displayName = "R$id"
}