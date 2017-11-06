package com.rubylichtenstein.rxkotlintest

import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.Result
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver

interface RxMatcher<T> : Matcher<TestObserver<T>>

fun <T> compose(action: (TestObserver<T>) -> Boolean, message: String): Matcher<TestObserver<T>> {
    return object : Matcher<TestObserver<T>> {
        override fun test(value: TestObserver<T>) = Result(action(value), message)
    }
}

typealias testAction<T> = (TestObserver<T>) -> Unit

fun <T> Observable<T>.test(action: TestObserver<T>.() -> Unit) = test().also(action)

fun <T> Observable<T>.testIt(action: testAction<T>) = test().also(action)
fun <T> Maybe<T>.testIt(action: testAction<T>) = test().also(action)
fun <T> Single<T>.testIt(action: testAction<T>) = test().also(action)
fun Completable.testIt(action: testAction<Void>) = test().also(action)

fun <T> test(action: (TestObserver<T>) -> Unit): RxMatcher<T> {
    return object : RxMatcher<T> {
        override fun test(value: TestObserver<T>) = try {
            action(value)
            Result(true, "")
        } catch (assertionError: AssertionError) {
            Result(false, assertionError.message.toString())

        }
    }
}
