package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.dsl.*

class Date : Example {
    override fun key(): String = "date"

    override fun graph(): Graph = graph {
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
}