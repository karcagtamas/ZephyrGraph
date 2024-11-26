package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.dsl.*

class Vacation : Example {
    override fun key(): String = "vacation"

    override fun graph(): Graph = graph {
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
}