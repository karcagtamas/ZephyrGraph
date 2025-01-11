package eu.karcags.ceg.graphmodel.expressions

/**
 * Interface for a testable type.
 */
interface TypeTestable {

    /**
     * Tests the given object instance by the object definition.
     * @return the validation error cause or null
     */
    fun test(): String?
}