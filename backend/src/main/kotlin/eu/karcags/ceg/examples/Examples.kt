package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.dsl.*

val dummyGraph = graph {
    variables {
        int("alma")
        float("korte")
        int("barack")
        boolean("koret")
    }

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
        cause("C2") { variable("korte") eq lit(1000f) }
        effect {
            description = "Hello. This is a description."
            "Hi"
        }
    }

    rule {
        and {
            cause("C3") { variable("korte") neq lit(1000f) }
            not { cause("C4") { variable("barack") eq variable("alma") } }
        }
        effect { "KORTE is better" }
    }

    rule {
        or {
            cause("C7") { lit(2.0f) gt variable("korte") }

            and {
                cause("C5") { lit(true) eq variable("koret") }
                cause("C6") { lit(0) + lit(0) lte variable("alma") }
                cause("C66") { variable("alma") isIn lit(1..2) }
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
    variables {
        int("month")
        int("day")
        int("year")
    }

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

val vacationGraph = graph {
    variables {
        int("age")
        int("service")
    }

    cause("C-ageint") { variable("age") isIn lit(18..44) }
    cause("C-service30>=") { variable("service") gte lit(30) }
    cause("C-age60") { variable("age") gte lit(60) }
    cause("C-service30<") { variable("service") lt lit(30) }

    rule {
        cause("C1") { variable("age") lt lit(18) }
        effect { "asd as " }
    }

    rule {
        and {
            causeById("C-ageint")
            cause("C22") { variable("service") lt lit(15) }
        }
        effect { "aasdasdsd" }
    }

    rule {
        and {
            causeById("C-ageint")
            cause("C32") { variable("service") isIn lit(15..29) }
        }
        effect { "aasdasdsd" }
    }

    rule {
        and {
            cause("C41") { variable("age") isIn lit(18..59) }
            causeById("C-service30>=")
        }
        effect { "aasdasdsd" }
    }

    rule {
        and {
            cause("C51") { variable("age") isIn lit(45..59) }
            causeById("C-service30<")
        }
        effect { "aasdasdsd" }
    }

    rule {
        and {
            causeById("C-age60")
            causeById("C-service30<")
        }
        effect { "aasdasdsd" }
    }

    rule {
        and {
            causeById("C-age60")
            causeById("C-service30>=")
        }
        effect { "aasdasdsd" }
    }
}