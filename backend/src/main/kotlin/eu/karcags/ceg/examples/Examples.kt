package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.dsl.*

val dummyGraph = graph {
    cause("C10") { "ALMA" }

    rule {
        cause("C1") {
            description = "This is another description."
            "1 + 2 = 4"
        }
        effect {
            description = "Hello. This is a description."
            "Hi"
        }
    }

    rule {
        cause("C2") { "12 + 12 > 2" }
        effect {
            description = "Hello. This is a description."
            "Hi"
        }
    }

    rule {
        and {
            cause("C3") { "a > b" }
            not { cause("C4") { "asd > 12" } }
        }
        effect { "KORTE is better" }
    }

    rule {
        or {
            cause("C7") { "a + b = c" }

            and {
                cause("C5") { "12 + 12 < a" }
                cause("C6") { "12 + 12 > a" }
            }
        }
        effect { "ALMA is good" }
    }

    rule {
        causeById("C10")
        effect { "IGEN" }
    }
}