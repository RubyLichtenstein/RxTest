package com.rubylichtenstein.rxtest.assertions

import com.rubylichtenstein.rxtest.matchers.Matcher
import com.rubylichtenstein.rxtest.matchers.never
import com.rubylichtenstein.rxtest.matchers.value
import io.reactivex.functions.Predicate
import io.reactivex.observers.BaseTestConsumer
import java.lang.AssertionError

/**
 * @param T the type of the stream.
 * @param U is TestObserver or TestSubscriber of type T
 * @see io.reactivex.observers.TestObserver<T>
 * @see io.reactivex.subscribers.TestSubscriber<T>
 *
 * @property matcher to assert.
 * matcher: Matcher<BaseTestConsumer<T, U>>
 * @throws AssertionError
 * @return Unit on success or throws assertion error on failure
 */
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.should(matcher: Matcher<BaseTestConsumer<T, U>>) =
    assertThat<T, U>(this, matcher)

/**
 * @param T the type of the stream.
 * @param U is TestObserver or TestSubscriber of type T
 * @see io.reactivex.observers.TestObserver<T>
 * @see io.reactivex.subscribers.TestSubscriber<T>
 *
 * @property matcher to assert.
 * matcher: Matcher<BaseTestConsumer<T, U>>
 * @throws AssertionError
 * @return Unit on success or throws assertion error on failure
 */
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldHave(matcher: Matcher<BaseTestConsumer<T, U>>) =
    should(matcher)

/**
 * @param T the type of the stream.
 * @param U is TestObserver or TestSubscriber of type T
 * @see io.reactivex.observers.TestObserver<T>
 * @see io.reactivex.subscribers.TestSubscriber<T>
 *
 * @property matcher to assert.
 * matcher: Matcher<BaseTestConsumer<T, U>>
 * @throws AssertionError
 * @return Unit on success or throws assertion error on failure
 */
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldBe(matcher: Matcher<BaseTestConsumer<T, U>>) =
    should(matcher)

/**
 * @param T the type of the stream.
 * @param U is TestObserver or TestSubscriber of type T
 * @see io.reactivex.observers.TestObserver<T>
 * @see io.reactivex.subscribers.TestSubscriber<T>
 *
 * @property matcher to assert.
 * matcher: Matcher<BaseTestConsumer<T, U>>
 * @throws AssertionError
 * @return Unit on success or throws assertion error on failure
 */
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(matcher: Matcher<BaseTestConsumer<T, U>>) =
    should(matcher)

/**
 * @param T the type of the stream.
 * @param U is TestObserver or TestSubscriber of type T
 * @see io.reactivex.observers.TestObserver<T>
 * @see io.reactivex.subscribers.TestSubscriber<T>
 *
 * @property matcher to assert.
 * matcher: Matcher<BaseTestConsumer<T, U>>
 * @throws AssertionError
 * @return Unit on success or throws assertion error on failure
 */
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(t: T) = shouldHave(value(t))

/**
 * @param T the type of the stream.
 * @param U is TestObserver or TestSubscriber of type T
 * @see io.reactivex.observers.TestObserver<T>
 * @see io.reactivex.subscribers.TestSubscriber<T>
 *
 * @property matcher to assert.
 * matcher: Matcher<BaseTestConsumer<T, U>>
 * @throws AssertionError
 * @return Unit on success or throws assertion error on failure
 */
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldEmit(t: Predicate<T>) = shouldHave(value(t))

/**
 * @param T the type of the stream.
 * @param U is TestObserver or TestSubscriber of type T
 * @see io.reactivex.observers.TestObserver<T>
 * @see io.reactivex.subscribers.TestSubscriber<T>
 *
 * @property matcher to assert.
 * matcher: Matcher<BaseTestConsumer<T, U>>
 * @throws AssertionError
 * @return Unit on success or throws assertion error on failure
 */
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldNeverEmit(t: T) = should(never(t))

/**
 * @param T the type of the stream.
 * @param U is TestObserver or TestSubscriber of type T
 * @see io.reactivex.observers.TestObserver<T>
 * @see io.reactivex.subscribers.TestSubscriber<T>
 *
 * @property matcher to assert.
 * matcher: Matcher<BaseTestConsumer<T, U>>
 * @throws AssertionError
 * @return Unit on success or throws assertion error on failure
 */
infix fun <T, U : BaseTestConsumer<T, U>> BaseTestConsumer<T, U>.shouldNeverEmit(t: Predicate<T>) = should(never(t))

internal fun <T, U : BaseTestConsumer<T, U>> assertThat(
    baseTestConsumer: BaseTestConsumer<T, U>,
    matcher: Matcher<BaseTestConsumer<T, U>>
) = with(matcher.test(baseTestConsumer)) {
    if (!passed) {
        throw AssertionError(failMessage)
    }
}
