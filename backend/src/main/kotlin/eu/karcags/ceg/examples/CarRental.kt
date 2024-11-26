package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.dsl.*

class CarRental : Example {
    override fun key(): String = "carRental"

    override fun graph(): Graph = graph {
        variables {
            int("cars")
            int("bikes")
        }

        rule {
            cause("C1") { variable("cars") gt 2 }
            effect { "Can 1 bike for free" }
        }
    }
}