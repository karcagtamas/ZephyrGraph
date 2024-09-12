package eu.karcags.ceg.parser

import eu.karcags.ceg.common.exceptions.GraphException
import eu.karcags.ceg.graph.Graph
import javax.script.ScriptEngine

class ScriptParser(val engine: ScriptEngine) : AbstractParser<Graph>() {

    override fun parse(content: String): Graph {
        val result = engine.eval(content)

        if (result is Graph) {
            return result
        }

        throw GraphException.ParseException("Result cannot be casted")
    }

    override fun prepare(original: String): String {
        return original.trim()
    }

    override fun validate(content: String): Boolean {
        return super.validate(content)
    }
}