package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.dsl.*

class PriceCalculation : Example {
    override fun key(): String = "priceCalculation"

    override fun graph(): Graph = graph {
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
}