package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.Graph

interface Example {

    fun key(): String

    fun graph(): Graph
}