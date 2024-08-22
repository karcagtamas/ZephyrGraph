package eu.karcags.ceg.graph.validators

import eu.karcags.ceg.common.exceptions.GraphException
import eu.karcags.ceg.graph.Definition

class DefinitionValidator : AbstractValidator<Definition>(Definition::class.java) {

    override fun validate(data: Definition?): Boolean {
        val data = required(data)

        if (data.expression == null && data.statement == null) {
            throw GraphException.ValidateException("Definition must have expression or statement")
        }

        return true
    }

}