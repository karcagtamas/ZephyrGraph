package eu.karcags.examples

import eu.karcags.graph.dsl.*

val dummyGraph = graph {
    val e1 = effect { displayName = "E1" }

    rule {
        source = cause { displayName = "C1" }
        target = e1
    }

    rule {
        source = cause { displayName = "C2" }
        target = e1
    }

    rule {
        val c3 = cause { displayName = "C3" }
        val c4 = cause { displayName = "C4" }

        source = c3 and c4
        target = effect { displayName = "E2" }
    }

    rule {
        val c5 = cause { displayName = "C5" }
        val c6 = cause { displayName = "C6" }
        val c7 = cause { displayName = "C7" }

        source = c7 or (c5 and c6)
        target = effect { displayName = "E2" }
    }
}