package eu.karcags.ceg.generator

class BVA {

    fun construct(items: List<Item>): List<List<TestType>> {
        // Init
        val interval = items.filter { it.type == DefinitionType.Intervals }.foldIndexed(Interval(0, emptyList())) { idx, acc, _ -> acc.increase(idx) }

        val result = mutableListOf<List<TestType>>()
        result.add(items.phase1())

        val base = items.map {
            when (it.type) {
                DefinitionType.Equal -> TestType.ON
                DefinitionType.NotEqual -> TestType.IN
                DefinitionType.IsTrue -> TestType.TRUE
                DefinitionType.IsFalse -> TestType.FALSE
                DefinitionType.Intervals -> TestType.IN
                else -> TestType.IN
            }
        }

        result.add(items.phase2())
        result.addAll(items.phase3(base))
        result.addAll(phase4(interval, base))

        return result
    }

    private fun List<Item>.phase1(): List<TestType> {
        return map {
            when (it.type) {
                DefinitionType.Equal -> TestType.ON
                DefinitionType.NotEqual -> TestType.IN2
                DefinitionType.IsTrue -> TestType.TRUE
                DefinitionType.IsFalse -> TestType.FALSE
                DefinitionType.Intervals -> TestType.ON1
                else -> TestType.ININ
            }
        }
    }

    private fun List<Item>.phase2(): List<TestType> {
        return map {
            when (it.type) {
                DefinitionType.NotEqual -> TestType.IN1
                DefinitionType.IsTrue -> TestType.TRUE
                DefinitionType.IsFalse -> TestType.FALSE
                DefinitionType.Intervals -> TestType.ON2
                else -> TestType.ON
            }
        }
    }

    private fun List<Item>.phase3(base: List<TestType>): List<List<TestType>> {
        return mapIndexed { idx, it ->
            when (it.type) {
                DefinitionType.Equal -> listOf(base.changeIndex(idx, TestType.OUT1), base.changeIndex(idx, TestType.OUT2))
                DefinitionType.NotEqual -> listOf(base.changeIndex(idx, TestType.OFF))
                DefinitionType.IsTrue -> listOf(base.changeIndex(idx, TestType.FALSE))
                DefinitionType.IsFalse -> listOf(base.changeIndex(idx, TestType.TRUE))
                else -> listOf(base.changeIndex(idx, TestType.OFF), base.changeIndex(idx, TestType.OUT))
            }
        }.flatten()
    }

    private fun phase4(interval: Interval, base: List<TestType>): List<List<TestType>> {
        if (interval.number > 0) {
            return interval.values.map { v ->
                listOf(TestType.OUT1, TestType.OUT2, TestType.OFF1, TestType.OFF2)
                    .map { type ->
                        base.changeIndex(v, type)
                    }
                    .flatten()
            }
        }

        return emptyList()
    }

    private fun <T> List<T>.changeIndex(index: Int, value: T): List<T> {
        return mapIndexed { idx, it -> if (idx == index) value else it }
    }

    data class Item(val type: DefinitionType)

    data class Interval(val number: Int, val values: List<Int>) {
        fun increase(value: Int): Interval {
            return Interval(value + 1, values + value)
        }
    }
}