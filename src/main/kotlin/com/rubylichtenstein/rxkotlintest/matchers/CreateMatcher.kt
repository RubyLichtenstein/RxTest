package com.rubylichtenstein.rxkotlintest.matchers


import io.reactivex.observers.TestObserver
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

data class AssertionResult(val passed: Boolean, val message: String)

val passedMessage = ""

class CreateMatcher<T>(private val assertion: (TestObserver<T>) -> Boolean,
                       private val mismatchMessage: String,
                       private val matchMessage: String) : TypeSafeMatcher<TestObserver<T>>() {

    override fun describeMismatchSafely(item: TestObserver<T>?, mismatchDescription: Description?) {
        super.describeMismatchSafely(item, mismatchDescription?.appendText(mismatchMessage))
    }

    override fun describeTo(description: Description) {
        description.appendText(matchMessage)
    }

    override fun matchesSafely(testObserver: TestObserver<T>): Boolean {
        return assertion(testObserver)
    }
}

class AssertionToMatcher<T>(private val assertion: (TestObserver<T>) -> Unit,
                            private var matchMessage: String = "Empty") : TypeSafeMatcher<TestObserver<T>>() {
    var mismatchMessage = "";

    override fun describeMismatchSafely(item: TestObserver<T>?, mismatchDescription: Description?) {
        super.describeMismatchSafely(item, mismatchDescription?.appendText(mismatchMessage))
    }

    override fun describeTo(description: Description) {
        description.appendText(matchMessage)
    }

    override fun matchesSafely(testObserver: TestObserver<T>)
            = with(applyAssertion(testObserver, assertion)) {
        mismatchMessage = message
        passed
    }
}



fun <T> applyAssertion(testObserver: TestObserver<T>,
                       assertion: (TestObserver<T>) -> Unit): AssertionResult {
    return try {
        assertion(testObserver)
        AssertionResult(true, passedMessage)
    } catch (assertionError: AssertionError) {
        AssertionResult(false, assertionError.message.toString())
    }
}
