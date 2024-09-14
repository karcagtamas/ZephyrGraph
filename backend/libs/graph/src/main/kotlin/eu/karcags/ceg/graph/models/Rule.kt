package eu.karcags.ceg.graph.models

data class Rule(val cause: Node, val effect: Node.Effect) {
    var id: String = "${cause.id}-${effect.id}"
}