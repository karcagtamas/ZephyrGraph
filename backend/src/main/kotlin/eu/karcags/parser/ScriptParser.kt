package eu.karcags.parser

import eu.karcags.graph.Graph
import javax.script.ScriptEngineManager

class ScriptParser : AbstractParser<Graph>() {

    override fun parse(content: String): Graph {
        val engine = ScriptEngineManager().getEngineByExtension("kts")
        val result = engine.eval(content)

        return result as Graph
    }

    override fun prepare(original: String): String {
        return original.trim()
    }

    override fun validate(content: String): Boolean {
        return super.validate(content)
    }
}