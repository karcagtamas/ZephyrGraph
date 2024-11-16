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

class GraphService {

    fun parse(source: String): Graph {
        val engine = ScriptEngineManager().getEngineByExtension("kts")
        return ScriptParser(engine).parse(source)
    }

    fun <T> parseGraph(obj: ParseObject, mapper: (Graph) -> T): T {
        if (obj.content.isBlank() || obj.content.isEmpty()) {
            throw GraphParseException("Received content is empty")
        }

        return parse(obj.content).let(mapper)
    }

    fun visual(graph: Graph): VisualGraph {
        return graph.toVisualGraph()
    }

    fun logical(graph: Graph): LogicalGraph {
        return graph.toLogicalGraph()
    }

    fun steppedLogical(graph: Graph): LogicalGraphConverter.SteppedLogicalGraph {
        return graph.toSteppedLogicalGraph()
    }

    fun simpleLogical(graph: Graph): Map<String, List<String>> {
        return graph.toLogicalGraph().stringify(DefaultSignResource()).let { items -> mapOf(Pair("definitions", items)) }
    }

    fun decisionTable(graph: Graph): DecisionTable {
        return decisionTable(logical(graph))
    }

    fun decisionTable(logicalGraph: LogicalGraph): DecisionTable {
        return DecisionTable.from(logicalGraph).optimize()
    }

    fun export(graph: Graph): String {
        return export(decisionTable(graph))
    }

    fun export(decisionTable: DecisionTable): String {
        return Exporter(decisionTable).export()
    }

    fun bva(graph: Graph): List<BVA.FinalResult> {
        return bva(logical(graph))
    }

    fun bva(logicalGraph: LogicalGraph): List<BVA.FinalResult> {
        return logicalGraph.toBVA()
    }

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

    @Serializable
    data class ParseObject(val content: String)

    @Serializable
    data class ParseResult(val visual: VisualGraph, val logical: LogicalResult, val decisionTable: DecisionTable.Export, val bva: List<BVA.FinalResult>, val export: String)

    @Serializable
    data class LogicalResult(val final: LogicalItemResult, val prevSteps: List<LogicalItemResult>)

    @Serializable
    data class LogicalItemResult(val key: String, val definitions: List<String>) {

        constructor(item: LogicalGraphConverter.SteppedRefinerItem, resource: AbstractSignResource) : this(
            item.key,
            item.graph.stringify(resource)
        )
    }
}

