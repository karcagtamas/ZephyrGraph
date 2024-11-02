package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.dsl.*

val dummyGraph = graph {
    cause("C10") { variable("alma") lt lit(12) }

    rule {
        cause("C1") {
            description = "This is another description."
            variable("alma") gt lit(20)
        }
        effect {
            description = "Hello. This is a description."
            "Hi"
        }
    }

    rule {
        cause("C2") { variable("korte") eq lit(1000) }
        effect {
            description = "Hello. This is a description."
            "Hi"
        }
    }

    rule {
        and {
            cause("C3") { variable("korte") neq lit(1000) }
            not { cause("C4") { variable("barack") eq variable("alma") } }
        }
        effect { "KORTE is better" }
    }

    rule {
        or {
            cause("C7") { lit(2.0) gt variable("korte") }

            and {
                cause("C5") { lit(true) eq variable("koret") }
                cause("C6") { lit(0) + lit(0) lte variable("alma") }
            }
        }
        effect { "ALMA is good" }
    }

    rule {
        causeById("C10")
        effect { "IGEN" }
    }
}

val dateGraph = graph {
    rule {
        and {
            and {
                cause("C11") { lit(1) lte variable("month") }
                cause("C12") { variable("month") lte lit(12) }
            }
            and {
                cause("C21") { lit(1) lte variable("day") }
                cause("C22") { variable("day") lte lit(31) }
            }
            and {
                cause("C31") { lit(1900) lte variable("year") }
                cause("C32") { variable("year") lte lit(2000) }
            }
        }

        effect { "Date is valid" }
    }
}