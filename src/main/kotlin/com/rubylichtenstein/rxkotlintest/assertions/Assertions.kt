package com.rubylichtenstein.rxkotlintest.assertions

import com.rubylichtenstein.rxkotlintest.matchers.never
import com.rubylichtenstein.rxkotlintest.matchers.value
import io.reactivex.observers.TestObserver
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert


infix fun <T> TestObserver<T>.should(matcher: Matcher<TestObserver<T>>) = MatcherAssert.assertThat(this, matcher)
infix fun <T> TestObserver<T>.shouldHave(matcher: Matcher<TestObserver<T>>) = should(matcher)
infix fun <T> TestObserver<T>.shouldBe(matcher: Matcher<TestObserver<T>>) = should(matcher)
infix fun <T> TestObserver<T>.shouldEmit(matcher: Matcher<TestObserver<T>>) = shouldHave(matcher)
infix fun <T> TestObserver<T>.shouldEmit(t: T) = shouldHave(value(t))
infix fun <T> TestObserver<T>.shouldEmit(t: (T) -> Boolean) = shouldHave(value(t))
infix fun <T> TestObserver<T>.shouldNeverEmit(t: T) = shouldHave(never(t))
infix fun <T> TestObserver<T>.shouldNeverEmit(t: (T) -> Boolean) = shouldHave(never(t))
