package com.rubylichtenstein.rxkotlintest.matchers


import io.reactivex.observers.BaseTestConsumer
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

data class AssertionResult(val passed: Boolean, val message: String)

val passedMessage = ""

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
                    mismatchMessage = message
                    passed
                }
    }
}

abstract class TestConsumerMatcher<T, U : BaseTestConsumer<T, U>>(private val matchMessage: String,
                                                                  var mismatchMessage: String = "")
    : TypeSafeMatcher<BaseTestConsumer<T, U>>() {

    override fun describeMismatchSafely(item: BaseTestConsumer<T, U>, mismatchDescription: Description?) {
        super.describeMismatchSafely(item, mismatchDescription?.appendText(mismatchMessage))
    }

    override fun describeTo(description: Description) {
        description.appendText(matchMessage)
    }
}


fun <T, U : BaseTestConsumer<T, U>> applyAssertion(testObserver: BaseTestConsumer<T, U>,
                                                   assertion: (BaseTestConsumer<T, U>) -> Unit): AssertionResult {
    return try {
        assertion(testObserver)
        AssertionResult(true, passedMessage)
    } catch (assertionError: AssertionError) {
        AssertionResult(false, assertionError.message.toString())
    }
}
