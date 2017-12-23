package com.rubylichtenstein.rxkotlintest.matchers


import io.reactivex.observers.BaseTestConsumer
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

data class AssertionResult(val passed: Boolean,
                           val error: AssertionError?)

/**
 * Create matcher by applying assertion on BaseTestConsumer
 *
 * @assertion assertion to apply, return true for success.
 * @param matchMessage message for success
 * @param mismatchMessage message for failing test
 */
fun <T, U : BaseTestConsumer<T, U>> createMatcher(assertion: (BaseTestConsumer<T, U>) -> Boolean,
                                                  matchMessage: String,
                                                  mismatchMessage: String): TypeSafeMatcher<BaseTestConsumer<T, U>> {
    return object : TypeSafeMatcher<BaseTestConsumer<T, U>>() {
        override fun describeMismatchSafely(item: BaseTestConsumer<T, U>, mismatchDescription: Description?) {
            super.describeMismatchSafely(item, mismatchDescription?.appendText(mismatchMessage))
        }

        override fun describeTo(description: Description) {
            description.appendText(matchMessage)
        }

        override fun matchesSafely(testObserver: BaseTestConsumer<T, U>): Boolean {
            return assertion(testObserver)
        }
    }
}

fun <T, U : BaseTestConsumer<T, U>> createMatcher(assertion: (BaseTestConsumer<T, U>) -> Unit,
                                                  matchMessage: String = ""): TestConsumerMatcher<T, U> {
    return object : TestConsumerMatcher<T, U>(matchMessage) {
        override fun matchesSafely(testObserver: BaseTestConsumer<T, U>) =
                with(applyAssertion(testObserver, assertion)) {
                    assertionError = error
                    passed
                }
    }
}

abstract class TestConsumerMatcher<T, U : BaseTestConsumer<T, U>>(private val matchMessage: String,
                                                                  var assertionError: AssertionError? = null)
    : TypeSafeMatcher<BaseTestConsumer<T, U>>() {

    override fun describeMismatchSafely(item: BaseTestConsumer<T, U>, mismatchDescription: Description?) {
        super.describeMismatchSafely(item, mismatchDescription?.appendText(assertionError?.message))
    }

    override fun describeTo(description: Description) {
        description.appendText(matchMessage)
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
                                                           assertion: (BaseTestConsumer<T, U>) -> Unit): AssertionResult {
    return try {
        assertion(testConsumer)
        AssertionResult(true, null)
    } catch (assertionError: AssertionError) {
        AssertionResult(false, assertionError)
    }
}
