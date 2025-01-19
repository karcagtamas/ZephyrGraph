package eu.karcags.ceg.services

import eu.karcags.ceg.generator.BVA
import eu.karcags.ceg.graph.converters.logical.LogicalGraph
import eu.karcags.ceg.graph.converters.logical.LogicalGraphConverter
import eu.karcags.ceg.graph.converters.logical.resources.AbstractSignResource
import eu.karcags.ceg.graph.converters.logical.resources.DefaultSignResource
import eu.karcags.ceg.graph.converters.toBVA
import eu.karcags.ceg.graph.converters.toLogicalGraph
import eu.karcags.ceg.graph.converters.toSteppedLogicalGraph
import eu.karcags.ceg.graph.converters.toVisualGraph
import eu.karcags.ceg.graph.converters.visual.VisualGraph
import eu.karcags.ceg.graph.decisiontable.DecisionTable
import eu.karcags.ceg.graph.exceptions.GraphParseException
import eu.karcags.ceg.graph.gpt.Exporter
import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.parser.ScriptParser
import kotlinx.serialization.Serializable
import javax.script.ScriptEngineManager

/**
 * Graph Service.
 * @constructor creates the graph service
 */
class GraphService {

    /**
     * Parses the source code into a graph entity.
     * @param source the source text
     * @return the compiled graph result
     */
    fun parse(source: String): Graph {
        val engine = ScriptEngineManager().getEngineByExtension("kts")
        return ScriptParser(engine).parse(source)
    }

    /**
     * Parses the input object into graph entity and maps by the given [mapper].
     * @param obj the input object
     * @param mapper to transform the graph result
     * @param T the result of the mapping
     * @throws GraphParseException when the object content is blank or empty
     */
    fun <T> parseGraph(obj: ParseObject, mapper: (Graph) -> T): T {
        if (obj.content.isBlank() || obj.content.isEmpty()) {
            throw GraphParseException("Received content is empty")
        }

        return parse(obj.content).let(mapper)
    }

    /**
     * Parses the graph into visual graph.
     * @param graph source graph
     * @return the converted visual graph
     */
    fun visual(graph: Graph): VisualGraph {
        return graph.toVisualGraph()
    }

    /**
     * Parses the graph into logical graph.
     * @param graph source graph
     * @return the converted logical graph
     */
    fun logical(graph: Graph): LogicalGraph {
        return graph.toLogicalGraph()
    }

    /**
     * Parses the graph into stepped logical graph.
     * @param graph source graph
     * @return the converted stepped logical graph
     */
    fun steppedLogical(graph: Graph): LogicalGraphConverter.SteppedLogicalGraph {
        return graph.toSteppedLogicalGraph()
    }

    /**
     * Parses the graph into simple logical definitions.
     * @param graph source graph
     * @return the converted logical definitions
     */
    fun simpleLogical(graph: Graph): Map<String, List<String>> {
        return graph.toLogicalGraph().stringify(DefaultSignResource()).let { items -> mapOf(Pair("definitions", items)) }
    }

    /**
     * Parses the graph into decision table.
     * @param graph source graph
     * @return the converted decision table
     */
    fun decisionTable(graph: Graph): DecisionTable {
        return decisionTable(logical(graph))
    }

    /**
     * Parses the logical graph into decision table.
     * @param logicalGraph source graph
     * @return the converted decision table
     */
    fun decisionTable(logicalGraph: LogicalGraph): DecisionTable {
        return DecisionTable.from(logicalGraph).optimize()
    }

    /**
     * Parses the graph into GPT export.
     * @param graph source graph
     * @return the converted GPT export
     */
    fun export(graph: Graph): String {
        return export(decisionTable(graph))
    }

    /**
     * Parses the decision table into GPT export.
     * @param decisionTable source decision table
     * @return the converted GPT export
     */
    fun export(decisionTable: DecisionTable): String {
        return Exporter(decisionTable).export()
    }

    /**
     * Parses the graph into BVA result.
     * @param graph source graph
     * @return the converted BVA result
     */
    fun bva(graph: Graph): List<BVA.FinalResult> {
        return bva(logical(graph))
    }

    /**
     * Parses the graph into BVA result.
     * @param logicalGraph source graph
     * @return the converted BVA result
     */
    fun bva(logicalGraph: LogicalGraph): List<BVA.FinalResult> {
        return logicalGraph.toBVA()
    }

    /**
     * Parses the graph into all possible result. (visual, logical, decision table, BVA, export)
     * @param graph source graph
     * @return the converted results
     */
    fun all(graph: Graph): ParseResult {
        val visual = visual(graph)
        val logical = steppedLogical(graph)
        val resource = DefaultSignResource()
        val decisionTable = decisionTable(logical.final.graph)
        return ParseResult(
            visual,
            LogicalResult(
                LogicalItemResult(logical.final, resource),
                logical.prevSteps.map { item -> LogicalItemResult(item, resource) }),
            decisionTable.export(),
            bva(logical.final.graph),
            export(decisionTable),
        )
    }

    /**
     * Parse object.
     * @property content the text content
     * @constructor creates a parse object
     * @param content the text content
     */
    @Serializable
    data class ParseObject(val content: String)

    /**
     * Parse result.
     * @property visual the visual graph
     * @property logical the logical result
     * @property decisionTable the decision table
     * @property bva BVA result
     * @property export GPT export
     * @constructor creates a parse object
     * @param visual the visual graph
     * @param logical the logical result
     * @param decisionTable the decision table
     * @param bva BVA result
     * @param export GPT export
     */
    @Serializable
    data class ParseResult(val visual: VisualGraph, val logical: LogicalResult, val decisionTable: DecisionTable.Export, val bva: List<BVA.FinalResult>, val export: String)

    /**
     * Logical result.
     * @property final final logical result step
     * @property prevSteps the previous logical steps
     * @constructor creates logical result
     * @param final final logical result step
     * @param prevSteps the previous logical steps
     */
    @Serializable
    data class LogicalResult(val final: LogicalItemResult, val prevSteps: List<LogicalItemResult>)

    /**
     * Logical item result.
     * @property key the key of the logical step
     * @property definitions the step definitions
     * @constructor creates logical item result
     * @param key the key of the logical step
     * @param definitions the step definitions
     */
    @Serializable
    data class LogicalItemResult(val key: String, val definitions: List<String>) {

        constructor(item: LogicalGraphConverter.SteppedRefinerItem, resource: AbstractSignResource) : this(
            item.key,
            item.graph.stringify(resource)
        )
    }
}

