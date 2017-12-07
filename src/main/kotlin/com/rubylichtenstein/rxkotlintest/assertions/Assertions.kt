package com.rubylichtenstein.rxkotlintest.assertions

import com.rubylichtenstein.rxkotlintest.matchers.TestConsumerMatcher
import com.rubylichtenstein.rxkotlintest.matchers.never
import com.rubylichtenstein.rxkotlintest.matchers.value
import io.reactivex.observers.BaseTestConsumer
import org.hamcrest.*

infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldHave(matcher: Matcher<BaseTestConsumer<T, U>>) = MatcherAssert.assertThat(this, matcher)

infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.should(matcher: TestConsumerMatcher<T, U>) = assertThat<T, U>(this, matcher)
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldHave(matcher: TestConsumerMatcher<T, U>) = should(matcher)
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldBe(matcher: TestConsumerMatcher<T, U>) = should(matcher)
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(matcher: TestConsumerMatcher<T, U>) = should(matcher)
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(t: T) = shouldHave(value(t))
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(t: (T) -> Boolean) = shouldHave(value(t))
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldNeverEmit(t: T) = should(never(t))
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldNeverEmit(t: (T) -> Boolean) = should(never(t))


fun <T, U : BaseTestConsumer<T, U>> assertThat(actual: BaseTestConsumer<T, U>, matcher: TestConsumerMatcher<in T, in U>) {
    if (!matcher.matches(actual)) {
        matcher.assertionError?.let { throw it }
    }
}