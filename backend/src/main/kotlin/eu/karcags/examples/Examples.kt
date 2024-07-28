package eu.karcags.examples

import eu.karcags.graph.dsl.*

val dummyGraph = graph {
    val e1 = effect {
        displayName = "E1"
        description = "Hello. This is a description."
    }

    rule {
        source = cause {
            displayName = "C1"
            description = "This is another description."
        }
        target = e1
    }

    rule {
        source = cause {
            displayName = "C2"

            definition = expression {
                expression = "12 + 12"
            }
        }
        target = e1
    }

    rule {
        val c3 = cause { displayName = "C3" }
        val c4 = cause {
            displayName = "C4"
            definition = expression {
                expression = "asd > 12"
            }
        }

        source = c3 and c4
        target = effect { displayName = "E2" }
    }

    rule {
        val c5 = cause { displayName = "C5" }
        val c6 = cause { displayName = "C6" }
        val c7 = cause { displayName = "C7" }

        source = c7 or (c5 and c6)
        target = effect {
            displayName = "E2"
            statement {
                statement = "ALMA is good"
            }
        }
    }
}