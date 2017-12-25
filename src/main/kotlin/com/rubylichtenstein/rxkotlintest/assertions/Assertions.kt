package com.rubylichtenstein.rxkotlintest.assertions

import com.rubylichtenstein.rxkotlintest.matchers.Matcher
import com.rubylichtenstein.rxkotlintest.matchers.never
import com.rubylichtenstein.rxkotlintest.matchers.value
import io.reactivex.functions.Predicate
import io.reactivex.observers.BaseTestConsumer


infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.should(matcher: Matcher<BaseTestConsumer<T, U>>)
        = assertThat<T, U>(this, matcher)

infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldHave(matcher: Matcher<BaseTestConsumer<T, U>>)
        = should(matcher)

infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldBe(matcher: Matcher<BaseTestConsumer<T, U>>)
        = should(matcher)

infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(matcher: Matcher<BaseTestConsumer<T, U>>)
        = should(matcher)

infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(t: T)
        = shouldHave(value(t))

//infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(t: (T) -> Boolean)
//        = shouldHave(value(t))

infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(t: Predicate<T>)
        = shouldHave(value(t))

infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldNeverEmit(t: T)
        = should(never(t))

//infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldNeverEmit(t: (T) -> Boolean)
//        = should(never(t))

infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldNeverEmit(t: Predicate<T>)
        = should(never(t))


fun <T, U : BaseTestConsumer<T, U>> assertThat(actual: BaseTestConsumer<T, U>, matcher: Matcher<BaseTestConsumer<T, U>>) {
    with(matcher.test(actual)) {
        if (!passed) {
            throw AssertionError(message)
        }
    }
}