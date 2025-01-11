package eu.karcags.ceg.graphmodel

/**
 * Graph definition.
 * Basically the container of the set of rules of the graph.
 * @property rules the rules of the graph
 * @constructor creates a graph definition.
 * @param rules the rules of the graph
 */
data class Graph(val rules: List<Rule>)