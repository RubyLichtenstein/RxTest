package com.rubylichtenstein.rxkotlintest

import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.Result
import io.kotlintest.matchers.should
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver

val passedMessage = ""

typealias testAction<T> = (TestObserver<T>) -> Unit
typealias tom<T> = TestObserverMatcher<T>

interface TestObserverMatcher<T> : Matcher<TestObserver<T>>


fun <T> compose(action: (TestObserver<T>) -> Boolean, message: String): tom<T> {
    return object : tom<T> {
        override fun test(value: TestObserver<T>) = Result(action(value), message)
    }
}

fun <T> assertionWrapper(action: (TestObserver<T>) -> Unit): tom<T> {
    return object : tom<T> {
        override fun test(value: TestObserver<T>) = try {
            action(value)
            Result(true, passedMessage)
        } catch (assertionError: AssertionError) {
            Result(false, assertionError.message.toString())
        }
    }
}

fun <T> Observable<T>.test(action: testAction<T>) = test().apply(action)
fun <T> Maybe<T>.test(action: testAction<T>) = test().apply(action)
fun <T> Single<T>.test(action: testAction<T>) = test().apply(action)
fun Completable.test(action: testAction<Void>) = test().apply(action)

infix fun <T> T.shouldBe(matcher: Matcher<T>) = should(matcher)
