package eu.karcags.ceg.graph.decisiontable

enum class TableItemValue {
    True,
    False,
    NotUsed;

    companion object {
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

    fun isSet(): Boolean {
        return this == True || this == False
    }
}