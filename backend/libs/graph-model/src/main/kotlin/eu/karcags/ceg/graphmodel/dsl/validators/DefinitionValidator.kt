package eu.karcags.ceg.graphmodel.dsl.validators

import eu.karcags.ceg.graphmodel.Definition
import eu.karcags.ceg.graphmodel.exceptions.GraphException

class DefinitionValidator : AbstractValidator<Definition>(Definition::class.java) {

    override fun validate(data: Definition?): Boolean {
        val data = required(data)

        if (data.expression == null && data.statement == null) {
            throw GraphException.ValidateException("Definition must have expression or statement")
        }

        return true
    }

}