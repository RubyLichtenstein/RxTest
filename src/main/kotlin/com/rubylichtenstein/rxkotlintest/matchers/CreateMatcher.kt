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
                                                  message: String)
        : Matcher<BaseTestConsumer<T, U>> {
    return object : Matcher<BaseTestConsumer<T, U>> {
        override fun test(value: BaseTestConsumer<T, U>): Result {
            return Result(assertion(value), message)
        }
    }
}

fun <T, U : BaseTestConsumer<T, U>> createMatcher(assertion: (BaseTestConsumer<T, U>) -> Unit): Matcher<BaseTestConsumer<T, U>> {
    return object : Matcher<BaseTestConsumer<T, U>> {
        override fun test(value: BaseTestConsumer<T, U>): Result {
            val ae = applyAssertion(value, assertion)
            return Result(ae == null, ae?.message)
        }
    }
}

//abstract class TestConsumerMatcher<T, U : BaseTestConsumer<T, U>>(private val matchMessage: String,
//                                                                  var assertionError: AssertionError? = null)
//    : TypeSafeMatcher<BaseTestConsumer<T, U>>() {
//
//    override fun describeTo(description: Description) {
//        description.appendText(matchMessage)
//    }
//}

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
