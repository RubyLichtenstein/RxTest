package com.rubylichtenstein.rxkotlintest.core

import com.rubylichtenstein.rxkotlintest.matchers.never
import com.rubylichtenstein.rxkotlintest.matchers.value
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.TypeSafeMatcher

data class AssertionResult(val passed: Boolean, val message: String)

fun <T> crateMatcher(assertion: (TestObserver<T>) -> Boolean,
                     message: String)
        : TypeSafeMatcher<TestObserver<T>> {
    return CreateMatcher(assertion, message)
}
private
class CreateMatcher<T>(private val assertion: (TestObserver<T>) -> Boolean,
                       private var matchMessage: String = "Empty") : TypeSafeMatcher<TestObserver<T>>() {
    var mismatchMessage = "";
    var mTestObserver: TestObserver<T>? = null

    override fun describeMismatchSafely(item: TestObserver<T>?, mismatchDescription: Description?) {
        super.describeMismatchSafely(item, mismatchDescription?.appendText(mismatchMessage))
    }

    override fun describeTo(description: Description) {
        description.appendText(matchMessage)
        mTestObserver.let {
            description.appendValue(mTestObserver)
        }
    }

    override fun matchesSafely(testObserver: TestObserver<T>) : Boolean{
        mTestObserver = testObserver
        return assertion(testObserver)
    }
}

class AssertionToMatcher<T>(private val assertion: (TestObserver<T>) -> Unit,
                            private var matchMessage: String = "Empty") : TypeSafeMatcher<TestObserver<T>>() {
    var mismatchMessage = "";
    var mTestObserver: TestObserver<T>? = null

    override fun describeMismatchSafely(item: TestObserver<T>?, mismatchDescription: Description?) {
        super.describeMismatchSafely(item, mismatchDescription?.appendText(mismatchMessage))
    }

    override fun describeTo(description: Description) {
        description.appendText(matchMessage)
        mTestObserver.let {
            description.appendValue(mTestObserver)
        }
    }

    override fun matchesSafely(testObserver: TestObserver<T>) = with(applyAssertion(testObserver, assertion)) {
        mTestObserver = testObserver
        mismatchMessage = message
        passed
    }
}

val passedMessage = ""


fun <T> applyAssertion(testObserver: TestObserver<T>,
                       assertion: (TestObserver<T>) -> Unit): AssertionResult {
    return try {
        assertion(testObserver)
        AssertionResult(true, passedMessage)
    } catch (assertionError: AssertionError) {
        AssertionResult(false, assertionError.message.toString())
    }
}

infix fun <T> TestObserver<T>.should(matcher: Matcher<TestObserver<T>>) = assertThat(this, matcher)
infix fun <T> TestObserver<T>.shouldHave(matcher: Matcher<TestObserver<T>>) = should(matcher)
infix fun <T> TestObserver<T>.shouldBe(matcher: Matcher<TestObserver<T>>) = should(matcher)
infix fun <T> TestObserver<T>.shouldEmit(matcher: Matcher<TestObserver<T>>) = shouldHave(matcher)
infix fun <T> TestObserver<T>.shouldEmit(t: T) = shouldHave(value(t))
infix fun <T> TestObserver<T>.shouldEmit(t: (T) -> Boolean) = shouldHave(value(t))
infix fun <T> TestObserver<T>.shouldNeverEmit(t: T) = shouldHave(never(t))
infix fun <T> TestObserver<T>.shouldNeverEmit(t: (T) -> Boolean) = shouldHave(never(t))

fun <T> Maybe<T>.test(action: (TestObserver<T>) -> Unit) = test().apply(action)
fun <T> Single<T>.test(action: (TestObserver<T>) -> Unit) = test().apply(action)
fun Completable.test(action: (TestObserver<Void>) -> Unit) = test().apply(action)
fun <T> Observable<T>.test(action: (TestObserver<T>) -> Unit) = test().apply(action)
