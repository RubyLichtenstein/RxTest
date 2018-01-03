package com.rubylichtenstein.rxtest.matchers


import io.reactivex.observers.BaseTestConsumer

/**
 * Create matcher
 * by applying assertion on BaseTestConsumer
 *
 * @assertion assertion to apply on testConsumer, true for success.
 * @param failMessage failMessage for test fail
 */
fun <T, U : BaseTestConsumer<T, U>> createMatcher(assertion: (BaseTestConsumer<T, U>) -> Boolean,
                                                  failMessage: String)
        : Matcher<BaseTestConsumer<T, U>> {
    return object : Matcher<BaseTestConsumer<T, U>> {
        override fun test(value: BaseTestConsumer<T, U>) =
                Result(assertion(value), failMessage)
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
 * @param assertion to apply on testConsumer - native RxJava assertions
 *
 * @return AssertionError? null in case od success
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
