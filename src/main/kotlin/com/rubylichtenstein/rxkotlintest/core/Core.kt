package com.rubylichtenstein.rxkotlintest.core

import com.rubylichtenstein.rxkotlintest.matchers.never
import com.rubylichtenstein.rxkotlintest.matchers.value
import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.Result
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldHave
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.hamcrest.Description
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.TypeSafeMatcher

data class AssertionResult(val passed: Boolean, val message: String)

class TestObserverMatcher<T>(private val assertion: (TestObserver<T>) -> Unit,
                             private val msg: String = "") : TypeSafeMatcher<TestObserver<T>>() {

    override fun describeTo(description: Description) {
        description.appendText(msg)
    }

    override fun matchesSafely(testObserver: TestObserver<T>)
            = applyAssertion(testObserver,assertion).passed
}

val passedMessage = ""

interface TestObserverMatcherDep<T> : Matcher<TestObserver<T>>

fun <T> matcher(action: (TestObserver<T>) -> Boolean, message: String): TestObserverMatcherDep<T> {
    return object : TestObserverMatcherDep<T> {
        override fun test(value: TestObserver<T>) = Result(action(value), message)
    }
}

fun <T> assertionToMatcher(assertion: (TestObserver<T>) -> Unit): TestObserverMatcherDep<T> {
    return object : TestObserverMatcherDep<T> {
        override fun test(value: TestObserver<T>)
                = with(applyAssertion(value, assertion)) {
            Result(passed, message)
        }
    }
}

fun <T> assertionToHamcrestMatcher(assertion: (TestObserver<T>) -> Unit) = TestObserverMatcher<T>(assertion)

fun <T> applyAssertion(testObserver: TestObserver<T>,
                       assertion: (TestObserver<T>) -> Unit): AssertionResult {
    return try {
        assertion(testObserver)
        AssertionResult(true, passedMessage)
    } catch (assertionError: AssertionError) {
        AssertionResult(false, assertionError.message.toString())
    }
}
infix fun <T> TestObserver<T>.should(matcher: TestObserverMatcher<T>) = assertThat(this, matcher)
//infix fun <T> TestObserver<T>.should(matcher: Matcher<T>) = shouldBe(matcher)

fun <T> Observable<T>.test(action: (TestObserver<T>) -> Unit) = test().apply(action)
fun <T> Maybe<T>.test(action: (TestObserver<T>) -> Unit) = test().apply(action)
fun <T> Single<T>.test(action: (TestObserver<T>) -> Unit) = test().apply(action)
fun Completable.test(action: (TestObserver<Void>) -> Unit) = test().apply(action)

infix fun <T> T.shouldBe(matcher: Matcher<T>) = should(matcher)
infix fun <T> T.shouldEmit(matcher: Matcher<T>) = should(matcher)
infix fun <T> TestObserver<T>.shouldEmit(t: T) = shouldHave(value(t))
infix fun <T> TestObserver<T>.shouldEmit(t: (T) -> Boolean) = shouldHave(value(t))
infix fun <T> TestObserver<T>.shouldNeverEmit(t: T) = shouldHave(never(t))
infix fun <T> TestObserver<T>.shouldNeverEmit(t: (T) -> Boolean) = shouldHave(never(t))

