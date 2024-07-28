package eu.karcags.graph.validators

import eu.karcags.common.exceptions.GraphException
import eu.karcags.graph.Definition

class DefinitionValidator : AbstractValidator<Definition>(Definition::class.java) {

    override fun validate(data: Definition?): Boolean {
        val data = required(data)

        if (data.expression == null && data.statement == null) {
            throw GraphException.ValidateException("Definition must have expression or statement")
        }

        return true
    }

}