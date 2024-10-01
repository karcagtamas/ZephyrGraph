package eu.karcags.ceg.graphmodel.dsl.builders

import eu.karcags.ceg.graphmodel.exceptions.GraphException

class DescriptionBuilder : AbstractBuilder<String>() {
    var description: String = ""

    override fun build(): String = description

    override fun validate(): Boolean {
        if (description.isEmpty()) {
            throw GraphException.ValidateException("Description cannot be empty")
        }

        return true
    }
}