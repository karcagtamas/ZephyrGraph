package eu.karcags.ceg.common

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class HelpersKtTest {

    @Test
    fun shouldNotChangeAnEmptyList() {
        assertIterableEquals(listOf<Int>(), listOf<Int>().changeByIndex(1, 2))
    }

    @Test
    fun shouldNotOutsideOfList() {
        assertIterableEquals(listOf(1, 2, 3, 4), listOf(1, 2, 3, 4).changeByIndex(10, 2))
    }

    @Test
    fun shouldChangeTheFirstElement() {
        assertIterableEquals(listOf(5, 2, 3, 4), listOf(1, 2, 3, 4).changeByIndex(0, 5))
    }

    @Test
    fun shouldChangeTheLastElement() {
        assertIterableEquals(listOf(1, 2, 3, 5), listOf(1, 2, 3, 4).changeByIndex(3, 5))
    }

    @Test
    fun shouldChangeInTheMiddle() {
        assertIterableEquals(listOf(1, 5, 3, 4), listOf(1, 2, 3, 4).changeByIndex(1, 5))
    }
}