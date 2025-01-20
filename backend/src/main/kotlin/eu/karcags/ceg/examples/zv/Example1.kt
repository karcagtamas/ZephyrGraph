package eu.karcags.ceg.examples.zv

import eu.karcags.ceg.examples.Example
import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.dsl.*

class Example1 : Example {
    override fun key(): String = "example-1"

    override fun graph(): Graph = graph {
        variables {
            int("age")
        }

        rule {
            cause("C1") { variable("age") gte 18 }
            effect { "The user is adult" }
        }
    }
}