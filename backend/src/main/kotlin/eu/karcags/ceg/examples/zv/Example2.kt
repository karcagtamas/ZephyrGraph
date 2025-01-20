package eu.karcags.ceg.examples.zv

import eu.karcags.ceg.examples.Example
import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.dsl.*

class Example2 : Example {
    override fun key(): String = "example-2"

    override fun graph(): Graph = graph {
        variables {
            float("cost")
            boolean("isMember")
            int("age")
        }

        cause("C-MEMBER") { variable("isMember") eq true }
        cause("C-SENIOR") { variable("age") gte 60 }

        rule {
            cause("C1") { variable("cost") lt 100f }
            effect { "No discount" }
        }

        rule {
            and {
                cause("C21") { variable("cost") isIn 100f..<400f }
                or {
                    causeById("C-MEMBER")
                    causeById("C-SENIOR")
                }
            }
            effect { "10% discount" }
        }

        rule {
            and {
                cause("C31") { variable("cost") gte 400f }
                or {
                    causeById("C-MEMBER")
                    causeById("C-SENIOR")
                }
            }
            effect { "15% discount" }
        }

        rule {
            and {
                cause("C41") { variable("cost") gte 400f }
                and {
                    causeById("C-MEMBER")
                    causeById("C-SENIOR")
                }
            }
            effect { "20% discount" }
        }
    }
}