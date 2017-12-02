package com.rubylichtenstein.rxkotlintest.assertions

import com.rubylichtenstein.rxkotlintest.matchers.never
import com.rubylichtenstein.rxkotlintest.matchers.value
import io.reactivex.observers.BaseTestConsumer
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert


//infix fun <T> TestObserver<T>.should(matcher: Matcher<TestObserver<T>>) = MatcherAssert.assertThat(this, matcher)

infix fun <T, U : BaseTestConsumer<T, U>>
        BaseTestConsumer<T, U>.should(matcher: Matcher<in BaseTestConsumer<T, U>>)
        = MatcherAssert.assertThat(this, matcher)

//infix fun <T> TestObserver<T>.should(matcher: Matcher<TestObserver<T>>)
//        = baseShould(matcher)
//
//infix fun <T> TestSubscriber<T>.should(matcher: Matcher<TestSubscriber<T>>)
//        = baseShould(matcher)

infix fun <T> TestObserver<T>.shouldHave(matcher: Matcher<BaseTestConsumer<T, TestObserver<T>>>) = baseShould(matcher)
infix fun <T> TestObserver<T>.shouldBe(matcher: Matcher<BaseTestConsumer<T, TestObserver<T>>>) = baseShould(matcher)
infix fun <T> TestObserver<T>.shouldEmit(matcher: Matcher<BaseTestConsumer<T, TestObserver<T>>>) = baseShould(matcher)
infix fun <T> TestObserver<T>.shouldEmit(t: T) = baseShould<TestObserver<T>>(value(t))
infix fun <T> TestObserver<T>.shouldEmit(t: (T) -> Boolean) = baseShould(value(t))
infix fun <T> TestObserver<T>.shouldNeverEmit(t: T) = baseShould(never(t))
infix fun <T> TestObserver<T>.shouldNeverEmit(t: (T) -> Boolean) = baseShould(never(t))
//infix fun <T> TestSubscriber<T>.shouldHave(matcher: Matcher<TestSubscriber<T>>) = should(matcher)
//infix fun <T> TestSubscriber<T>.shouldBe(matcher: Matcher<TestSubscriber<T>>) = should(matcher)
//infix fun <T> TestSubscriber<T>.shouldEmit(matcher: Matcher<TestSubscriber<T>>) = shouldHave(matcher)
//infix fun <T> TestSubscriber<T>.shouldEmit(t: T) = shouldHave(value(t))
//infix fun <T> TestSubscriber<T>.shouldEmit(t: (T) -> Boolean) = shouldHave(value(t))
//infix fun <T> TestSubscriber<T>.shouldNeverEmit(t: T) = shouldHave(never(t))
//infix fun <T> TestSubscriber<T>.shouldNeverEmit(t: (T) -> Boolean) = shouldHave(never(t))
