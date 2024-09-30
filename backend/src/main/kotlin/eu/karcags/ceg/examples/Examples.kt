package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.dsl.*

val dummyGraph = graph {
    cause("C10") {
        expression { "ALMA" }
    }

    rule {
        cause("C1") {
            description = "This is another description."

            expression { "1 + 2 = 4" }
        }

        effect {
            description = "Hello. This is a description."

            expression { "Hi" }
        }
    }

    rule {
        cause("C2") {
            expression { "12 + 12 > 2" }
        }

        effect {
            description = "Hello. This is a description."

            expression { "Hi" }
        }
    }

    rule {
        and {
            cause("C3") {
                expression { "a > b" }
            }

            // TODO: Negate C4 => !C4
            cause("C4") {
                expression { "asd > 12" }
            }
        }

        effect {
            expression { "KORTE is better" }
        }
    }

    rule {
        or {
            cause("C7") {
                expression { "a + b = c" }
            }

            and {
                cause("C5") {
                    expression { "12 + 12 < a" }
                }

                cause("C6") {
                    expression { "12 + 12 > a" }
                }
            }
        }

        effect {
            expression { "ALMA is good" }
        }
    }

    rule {
        causeById("C10")

        effect {
            expression { "IGEN" }
        }
    }
}