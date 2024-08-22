package eu.karcags.ceg.examples

import eu.karcags.ceg.graph.dsl.*

val dummyGraph = graph {
    val e1 = effect {
        displayName = "E1"
        description = "Hello. This is a description."

        statement { "Hi" }
    }

    rule {
        source = cause {
            displayName = "C1"
            description = "This is another description."

            expression { "1 + 2 = 4" }
        }
        target = e1
    }

    rule {
        source = cause {
            displayName = "C2"

            expression { "12 + 12 > 2" }
        }
        target = e1
    }

    rule {
        val c3 = cause {
            displayName = "C3"

            expression { "a > b" }
        }
        val c4 = cause {
            displayName = "C4"

            expression { "asd > 12" }
        }

        source = c3 and !c4
        target = effect {
            displayName = "E2"

            statement { "KORTE is better" }
        }
    }

    rule {
        val c5 = cause {
            displayName = "C5"

            expression { "12 + 12 < a" }
        }
        val c6 = cause {
            displayName = "C6"

            expression { "12 + 12 > a" }
        }
        val c7 = cause {
            displayName = "C7"

            expression { "a + b = c" }
        }

        source = c7 or (c5 and c6)
        target = effect {
            displayName = "E3"

            statement { "ALMA is good" }
        }
    }
}