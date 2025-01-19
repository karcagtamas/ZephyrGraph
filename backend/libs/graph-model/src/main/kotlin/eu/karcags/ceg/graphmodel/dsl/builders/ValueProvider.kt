package eu.karcags.ceg.graphmodel.dsl.builders

/**
 * Value provider store for any type of key-value pairs.
 * @property keyGetter key getter definition to extract the key from the added elements.
 * @property values the store of the values and its keys
 * @constructor creates a value provider
 * @param keyGetter key getter definition to extract the key from the added elements.
 * @param T the type of the keys
 * @param U the type of they values
 */
class ValueProvider<T, U>(private val keyGetter: (U) -> T) {

    private val values = mutableMapOf<T, U>()

    /**
     * Adds element to the store if not exists yet.
     * @param value the new value
     */
    fun add(value: U) {
        val key = keyGetter(value)

        if (!values.containsKey(key)) {
            values[key] = value
        }
    }

    /**
     * Finds value by key.
     * @param key the search key
     * @return the found value (null if it is not exist)
     */
    fun byKey(key: T): U? {
        return values[key]
    }

    /**
     * Gets all the values from the store.
     * @return collection of the values
     */
    fun all(): Collection<U> {
        return values.values
    }
}