package com.rubylichtenstein.rxkotlintest.assertions

import com.rubylichtenstein.rxkotlintest.matchers.never
import com.rubylichtenstein.rxkotlintest.matchers.value
import io.reactivex.observers.BaseTestConsumer
import io.reactivex.observers.TestObserver
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert


infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.should(matcher: Matcher<BaseTestConsumer<T, U>>) = MatcherAssert.assertThat(this, matcher)
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldHave(matcher: Matcher<BaseTestConsumer<T, U>>) = should(matcher)
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldBe(matcher: Matcher<BaseTestConsumer<T, U>>) = should(matcher)
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(matcher: Matcher<BaseTestConsumer<T, U>>) = should(matcher)
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(t: T) = shouldHave(value(t))
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(t: (T) -> Boolean) = shouldHave(value(t))
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldNeverEmit(t: T) = should(never(t))
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldNeverEmit(t: (T) -> Boolean) = should(never(t))
