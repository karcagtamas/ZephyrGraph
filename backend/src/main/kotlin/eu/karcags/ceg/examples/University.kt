package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.dsl.*

class University : Example {
    override fun key(): String = "university"

    override fun graph(): Graph = graph {
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
}