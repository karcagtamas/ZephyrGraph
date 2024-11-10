package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.dsl.*

val dummyGraph = graph {
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
            not { cause("C4") { variable("barack") eq variable("alma") } }
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

val dateGraph = graph {
    variables {
        int("month")
        int("day")
        int("year")
    }

    rule {
        and {
            and {
                cause("C11") { variable("month") gte 1 }
                cause("C12") { variable("month") lte 12 }
            }
            and {
                cause("C21") { variable("day") gte 1 }
                cause("C22") { variable("day") lte 31 }
            }
            and {
                cause("C31") { variable("year") gte 1900 }
                cause("C32") { variable("year") lte 2000 }
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

    cause("C-ageint") { variable("age") isIn 18..44 }
    cause("C-service30>=") { variable("service") gte 30 }
    cause("C-age60") { variable("age") gte 60 }
    cause("C-service30<") { variable("service") lt 30 }

    rule {
        cause("C1") { variable("age") lt 18 }
        effect { "asd as " }
    }

    rule {
        and {
            causeById("C-ageint")
            cause("C22") { variable("service") lt 15 }
        }
        effect { "aasdasdsd" }
    }

    rule {
        and {
            causeById("C-ageint")
            cause("C32") { variable("service") isIn 15..29 }
        }
        effect { "aasdasdsd" }
    }

    rule {
        and {
            cause("C41") { variable("age") isIn 18..59 }
            causeById("C-service30>=")
        }
        effect { "aasdasdsd" }
    }

    rule {
        and {
            cause("C51") { variable("age") isIn 45..59 }
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