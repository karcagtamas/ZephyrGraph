package eu.karcags.ceg.graph.decisiontable

/**
 * Table item values.
 */
enum class TableItemValue {
    True,
    False,
    NotUsed;

    companion object {
        /**
         * Determines the two given values are opposition of each other.
         * Two items are opposition of each other if the first is [True] and the second is [False] or the first is [False] and the second is [True].
         * @param a first item
         * @param b second item
         */
        fun opposites(a: TableItemValue?, b: TableItemValue?): Boolean {
            if (a == null || b == null) {
                return false
            }

            if ((a == True && b == False) || (a == False && b == True)) {
                return true
            }

            return false
        }
    }

    /**
     * Determines is the value is set ([True] or [False]).
     * @return True if the value is set
     */
    fun isSet(): Boolean {
        return this == True || this == False
    }
}