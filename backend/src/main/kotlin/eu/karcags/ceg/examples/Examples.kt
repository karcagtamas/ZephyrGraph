package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.dsl.*

val dummyGraph = graph {
    rule {
        cause = cause("C1") {
            description = "This is another description."

            expression { "1 + 2 = 4" }
        }

        effect {
            description = "Hello. This is a description."

            expression { "Hi" }
        }
    }

    rule {
        cause = cause("C2") {
            expression { "12 + 12 > 2" }
        }

        effect {
            description = "Hello. This is a description."

            expression { "Hi" }
        }
    }

    rule {
        val c3 = cause("C3") {
            expression { "a > b" }
        }
        val c4 = cause("C4") {
            expression { "asd > 12" }
        }

        cause = c3 and !c4

        effect {
            expression { "KORTE is better" }
        }
    }

    rule {
        val c5 = cause("C5") {
            expression { "12 + 12 < a" }
        }
        val c6 = cause("C6") {
            expression { "12 + 12 > a" }
        }
        val c7 = cause("C7") {
            expression { "a + b = c" }
        }

        cause = c7 or (c5 and c6)

        effect {
            expression { "ALMA is good" }
        }
    }
}