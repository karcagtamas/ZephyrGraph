package eu.karcags.ceg.parser

import eu.karcags.ceg.graph.exceptions.GraphParseException
import javax.script.ScriptEngine

class ScriptParser(val engine: ScriptEngine) : AbstractParser<eu.karcags.ceg.graphmodel.Graph>() {

    override fun parse(content: String): eu.karcags.ceg.graphmodel.Graph {
        val result = engine.eval(content)

        if (result is eu.karcags.ceg.graphmodel.Graph) {
            return result
        }

        throw GraphParseException("Result cannot be casted")
    }

    override fun prepare(original: String): String {
        return original.trim()
    }

    override fun validate(content: String): Boolean {
        return super.validate(content)
    }
}