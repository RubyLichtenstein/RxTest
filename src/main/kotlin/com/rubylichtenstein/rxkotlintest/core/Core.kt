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

val passedMessage = ""

interface TestObserverMatcher<T> : Matcher<TestObserver<T>>

fun <T> matcher(action: (TestObserver<T>) -> Boolean, message: String): TestObserverMatcher<T> {
    return object : TestObserverMatcher<T> {
        override fun test(value: TestObserver<T>) = Result(action(value), message)
    }
}

fun <T> assertionToMatcher(action: (TestObserver<T>) -> Unit): TestObserverMatcher<T> {
    return object : TestObserverMatcher<T> {
        override fun test(value: TestObserver<T>) = try {
            action(value)
            Result(true, passedMessage)
        } catch (assertionError: AssertionError) {
            Result(false, assertionError.message.toString())
        }
    }
}

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

