package eu.karcags.ceg.graphmodel.dsl.builders

class ValueProvider<T, U>(private val keyGetter: (U) -> T) {

    private val values = mutableMapOf<T, U>()

    fun add(value: U) {
        val key = keyGetter(value)

        if (!values.containsKey(key)) {
            values[key] = value
        }
    }

    fun byKey(key: T): U? {
        return values[key]
    }

    fun all(): Collection<U> {
        return values.values
    }
}