package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.dsl.*

class OnlineBookStore : Example {
    override fun key(): String = "onlineBookStore"

    override fun graph(): Graph = graph {
        variables {
            float("newPrice")
            float("secondPrice")
            boolean("card")
        }

        cause("priceAtLeast50") { variable("newPrice") gte 50f }
        cause("hasCard") { variable("card") eq true }

        rule {
            or {
                causeById("priceAtLeast50")
                causeById("hasCard")
            }

            effect { "10% price reduction" }
        }

        rule {
            and {
                causeById("priceAtLeast50")
                causeById("hasCard")
            }

            effect { "15% price reduction" }
        }

        rule {
            and {
                cause("C31") { variable("newPrice") gte 30f }
                cause("C32") { variable("secondPrice") gte 60f }
            }

            effect { "5% price reduction" }
        }
    }
}