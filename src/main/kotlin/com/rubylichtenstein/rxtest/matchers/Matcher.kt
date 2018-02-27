package com.rubylichtenstein.rxtest.matchers

/**
 * Generic matcher with logical OR/AND
 */
interface Matcher<T> {

    /**
     * apply test
     */
    fun test(value: T): Result

    /**
     * Logic AND
     */
    infix fun and(other: Matcher<T>): Matcher<T> = object : Matcher<T> {
        override fun test(value: T): Result {
            return andTest(value, other)
        }
    }

    /**
     * Logic OR
     */
    infix fun or(other: Matcher<T>): Matcher<T> = object : Matcher<T> {
        override fun test(value: T): Result {
            return orTest(value, other)
        }
    }

    private fun andTest(
        value: T,
        other: Matcher<T>
    ): Result {
        val r = this.test(value)
        return if (!r.passed)
            r
        else
            other.test(value)
    }

    private fun orTest(
        value: T,
        other: Matcher<T>
    ): Result {
        val r = this.test(value)
        return if (r.passed)
            r
        else
            other.test(value)
    }
}

/**
 * Matcher test result
 */
data class Result(val passed: Boolean, val failMessage: String?)
