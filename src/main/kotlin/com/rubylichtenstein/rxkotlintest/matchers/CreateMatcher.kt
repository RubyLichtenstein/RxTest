package com.rubylichtenstein.rxkotlintest.matchers


import io.reactivex.observers.BaseTestConsumer

/**
 * Create matcher by applying assertion on BaseTestConsumer
 *
 * @assertion assertion to apply, return true for success.
 * @param matchMessage message for success
 * @param mismatchMessage message for failing test
 */
fun <T, U : BaseTestConsumer<T, U>> createMatcher(assertion: (BaseTestConsumer<T, U>) -> Boolean,
                                                  message: String)
        : Matcher<BaseTestConsumer<T, U>> {
    return object : Matcher<BaseTestConsumer<T, U>> {
        override fun test(value: BaseTestConsumer<T, U>) = Result(assertion(value), message)
    }
}

fun <T, U : BaseTestConsumer<T, U>> createMatcher(assertion: (BaseTestConsumer<T, U>) -> Unit)
        : Matcher<BaseTestConsumer<T, U>> {
    return object : Matcher<BaseTestConsumer<T, U>> {
        override fun test(value: BaseTestConsumer<T, U>): Result {
            applyAssertion(value, assertion).let {
                return Result(it == null, it?.message)
            }
        }
    }
}

/**
 * Apply rx java assertion on testConsumer, delegating AssertionError as AssertionResult
 *
 * @param testConsumer to apply assertion on
 * @param assertion to apply on testConsumer
 *
 * @return AssertionResult contains Assertion error in case of assertion fails
 */
private fun <T, U : BaseTestConsumer<T, U>> applyAssertion(testConsumer: BaseTestConsumer<T, U>,
                                                           assertion: (BaseTestConsumer<T, U>) -> Unit)
        : AssertionError? {
    return try {
        assertion(testConsumer)
        null
    } catch (assertionError: AssertionError) {
        assertionError
    }
}
