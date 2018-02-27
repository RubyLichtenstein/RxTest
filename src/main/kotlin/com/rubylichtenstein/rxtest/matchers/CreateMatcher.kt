package com.rubylichtenstein.rxtest.matchers

import io.reactivex.observers.BaseTestConsumer

/**
 * Create matcher by applying assertion on BaseTestConsumer
 *
 * @assertion assertion to apply on testConsumer,should return true on success.
 * @param failMessage to show in case of failure
 */
inline fun <T, U : BaseTestConsumer<T, U>> createMatcher(
    crossinline assertion: (BaseTestConsumer<T, U>) -> Boolean,
    failMessage: String
): Matcher<BaseTestConsumer<T, U>> {
    return object : Matcher<BaseTestConsumer<T, U>> {
        override fun test(value: BaseTestConsumer<T, U>) = Result(assertion.invoke(value), failMessage)
    }
}

/**
 * Create matcher by applying assertion on BaseTestConsumer
 *
 * @assertion native rx assertion to apply on BaseTestConsumer, should throws AssertionError on failure.
 * @return Matcher
 */
internal inline fun <T, U : BaseTestConsumer<T, U>> createMatcher(crossinline assertion: (BaseTestConsumer<T, U>) -> Unit)
        : Matcher<BaseTestConsumer<T, U>> {
    return object : Matcher<BaseTestConsumer<T, U>> {
        override fun test(value: BaseTestConsumer<T, U>): Result {
            applyAssertion(value, assertion).let {
                return Result(it == null, it?.message)
            }
        }
    }
}

internal inline fun <T, U : BaseTestConsumer<T, U>> applyAssertion(
    testConsumer: BaseTestConsumer<T, U>,
    assertion: (BaseTestConsumer<T, U>) -> Unit
)
        : AssertionError? {
    return try {
        assertion(testConsumer)
        null
    } catch (assertionError: AssertionError) {
        assertionError
    }
}

