package com.rubylichtenstein.rxkotlintest.matchers


import io.reactivex.observers.BaseTestConsumer
import io.reactivex.observers.TestObserver
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

data class AssertionResult(val passed: Boolean, val message: String)

val passedMessage = ""

class CreateMatcher<T, U : BaseTestConsumer<T, U>>(
        private val assertion: (BaseTestConsumer<T, U>) -> Boolean,
        private val mismatchMessage: String,
        private val matchMessage: String) : TypeSafeMatcher<BaseTestConsumer<T, U>>() {

    override fun describeMismatchSafely(item: BaseTestConsumer<T, U>?, mismatchDescription: Description?) {
        super.describeMismatchSafely(item, mismatchDescription?.appendText(mismatchMessage))
    }

    override fun describeTo(description: Description) {
        description.appendText(matchMessage)
    }

    override fun matchesSafely(testObserver: BaseTestConsumer<T, U>): Boolean {
        return assertion(testObserver)
    }
}

class AssertionToMatcher<T, U : BaseTestConsumer<T, U>>(private val assertion: (BaseTestConsumer<T, in U>) -> Unit,
                                                        private var matchMessage: String = "Empty")
    : TypeSafeMatcher<BaseTestConsumer<T, U>>() {
    var mismatchMessage = "";

    override fun describeMismatchSafely(item: BaseTestConsumer<T, U>, mismatchDescription: Description?) {
        super.describeMismatchSafely(item, mismatchDescription?.appendText(mismatchMessage))
    }

    override fun describeTo(description: Description) {
        description.appendText(matchMessage)
    }

    override fun matchesSafely(testObserver: BaseTestConsumer<T, U>)
            = with(applyAssertion(testObserver, assertion)) {
        mismatchMessage = message
        passed
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
