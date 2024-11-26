package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.dsl.*

class Dummy : Example {
    override fun key(): String = "dummy"

    override fun graph(): Graph = graph {
        variables {
            int("alma")
            float("korte")
            int("barack")
            boolean("koret")
        }

        cause("C10") { variable("alma") lt 12 }

        rule {
            cause("C1") {
                description = "This is another description."
                variable("alma") gt 20
            }
            effect {
                description = "Hello. This is a description."
                "Hi"
            }
        }

        rule {
            cause("C2") { variable("korte") eq 1000f }
            effect {
                description = "Hello. This is a description."
                "Hi"
            }
        }

        rule {
            and {
                cause("C3") { variable("korte") neq 1000f }
                not { cause("C4") { variable("barack") eq lit(10) } }
            }
            effect { "KORTE is better" }
        }

        rule {
            or {
                cause("C7") { variable("korte") lt 2f }

                and {
                    cause("C5") { variable("koret") eq true }
                    cause("C6") { variable("alma") gte 0 + 0 }
                    cause("C66") { variable("alma") isIn 1..2 }
                }
            }
            effect { "ALMA is good" }
        }

        rule {
            causeById("C10")
            effect { "IGEN" }
        }
    }
}