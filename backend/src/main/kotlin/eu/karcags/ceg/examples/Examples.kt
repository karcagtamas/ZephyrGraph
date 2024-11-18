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

val dateGraph = graph {
    variables {
        int("month")
        int("day")
        int("year")
    }

    rule {
        and {
            cause("C1") { variable("month") isIn 1..12 }
            cause("C2") { variable("day") isIn 1..31 }
            cause("C3") { variable("year") isIn 1900..2000 }
        }

        effect { "Date is valid" }
    }
}

val vacationGraph = graph {
    variables {
        int("age")
        int("service")
    }

    cause("C-service30>=") { variable("service") gte 30 }
    cause("C-age60") { variable("age") gte 60 }
    cause("C-age18") { variable("age") lt 18 }

    rule {
        or {
            causeById("C-age18")
            causeById("C-age60")
            causeById("C-service30>=")
        }
        effect { "Will receive extra 5 days" }
    }

    rule {
        or {
            causeById("C-age60")
            causeById("C-service30>=")
        }
        effect { "Additional 3 days" }
    }

    rule {
        and {
            or {
                cause("C31") { variable("service") gte 15 }
                cause("C32") { variable("age") gte 45 }
            }
            not {
                or {
                    causeById("C-age18")
                    causeById("C-age60")
                    causeById("C-service30>=")
                }
            }
        }


        effect { "Extra 2 days" }
    }
}

val priceCalculationGraph = graph {
    variables {
        float("price", 0.1f)
        float("weight", 0.1f)
        boolean("creditCard")
    }

    rule {
        cause("C1") { variable("price") gte 200f }
        effect { "The customer gets 10% price reduction" }
    }

    rule {
        or {
            cause("C21") { variable("weight") lt 5f }
            cause("C22") { variable("price") gte 100f }
        }

        effect { "The delivery is free" }
    }

    rule {
        cause("C3") { variable("weight") gte 5f }
        effect { "The delivery equals with the weight" }
    }

    rule {
        cause("C4") { variable("creditCard") eq true }
        effect { "3% reduction from the reduced price of the goods" }
    }

    rule {
        and {
            cause("C51") { variable("price") gte 200f }
            cause("C52") { variable("creditCard") eq true }
            cause("C53") { variable("weight") lt 5f }
        }
        effect { "15% price reduction fro the original price of the goods" }
    }
}

val universityGraph = graph {
    variables {
        int("be")
        int("le")
        int("wp")
        int("sum")
    }

    cause("beIsGood") { variable("be") gte 25 }
    cause("leIsGood") { variable("le") gte 25 }
    cause("wpIsGood") { variable("wp") gte 25 }

    rule {
        and {
            cause("C11") { variable("be") isIn 0..50 }
            cause("C12") { variable("le") isIn 0..50 }
            cause("C13") { variable("wp") isIn 0..50 }
            cause("C14") { variable("sum") isIn 0..150 }
        }
        effect { "The points are valid" }
    }

    rule {
        or {
            cause("C21") { variable("be") lt 25 }
            cause("C22") { variable("be") lt 25 }
            cause("C23") { variable("be") lt 25 }
            cause("C24") { variable("sum") lt 76 }
        }
        effect { "The course is failed" }
    }

    rule {
        and {
            cause("C3") { variable("sum") isIn 76..100 }
            causeById("beIsGood")
            causeById("leIsGood")
            causeById("wpIsGood")
        }
        effect { "The course result is satisfactory" }
    }

    rule {
        and {
            cause("C4") { variable("sum") isIn 100..125 }
            causeById("beIsGood")
            causeById("leIsGood")
            causeById("wpIsGood")
        }
        effect { "The course result is good" }
    }

    rule {
        and {
            cause("C5") { variable("sum") gt 125 }
            causeById("beIsGood")
            causeById("leIsGood")
            causeById("wpIsGood")
        }
        effect { "The course result is very good" }
    }
}