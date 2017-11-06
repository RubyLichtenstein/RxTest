package com.rubylichtenstein.rxkotlintest

import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.should
import io.reactivex.observers.TestObserver

/**
 * Created by ruby on 27/10/17.
 */

fun <T> complete(): tom<T> =
        assertionWrapper({ it.assertComplete() })

fun <T> notComplete(): tom<T> =
        assertionWrapper({ it.assertNotComplete() })

fun <T> error(error: Throwable): tom<T>
        = assertionWrapper({ it.assertError(error) })

fun <T> error(errorClass: Class<out Throwable>): tom<T>
        = assertionWrapper({ it.assertError(errorClass) })

fun <T> error(errorPredicate: (Throwable) -> Boolean): tom<T>
        = assertionWrapper({ it.assertError(errorPredicate) })

fun <T> noErrors(): tom<T>
        = assertionWrapper({ it.assertNoErrors() })

fun <T> value(value: T): tom<T> = assertionWrapper({ it.assertValue(value) })
fun <T> value(valuePredicate: (T) -> Boolean): tom<T> = assertionWrapper({ it.assertValue(valuePredicate) })
fun <T> never(value: T): tom<T> = assertionWrapper({ it.assertNever(value) })
fun <T> never(neverPredicate: (T) -> Boolean): tom<T> = assertionWrapper({ it.assertNever(neverPredicate) })
fun <T> valueAt(index: Int, value: T): tom<T> = assertionWrapper({ it.assertValueAt(index, value) })
fun <T> valueAt(index: Int, valuePredicate: (T) -> Boolean): tom<T> = assertionWrapper({ it.assertValueAt(index, valuePredicate) })
fun <T> values(vararg values: T): tom<T> = assertionWrapper({ it.assertValues(*values) })
fun <T> empty(): tom<T> = assertionWrapper({
    it.assertSubscribed()
            .assertNoValues()
            .assertNoErrors()
            .assertNotComplete()
})

fun <T> noTimeout(): tom<T> = assertionWrapper({ it.assertNoTimeout() })
fun <T> timeout(): tom<T> = assertionWrapper({ it.assertTimeout() })
fun <T> subscribed(): tom<T> = assertionWrapper({ it.assertSubscribed() })
fun <T> notSubscribed(): tom<T> = assertionWrapper({ it.assertNotSubscribed() })

fun <T> failure(errorPredicate: (Throwable) -> Boolean, vararg values: T): tom<T>
        = assertionWrapper({ it.assertFailure(errorPredicate, values) })

fun <T> failure(error: Class<out Throwable>, vararg values: T): tom<T>
        = assertionWrapper({ it.assertFailure(error, *values) })

fun <T> failureAndMessage(error: Class<out Throwable>,
                          message: String,
                          vararg values: T): tom<T> = assertionWrapper(
        { it.assertFailureAndMessage(error, message, *values) })

fun <T> result(vararg values: T): tom<T> = assertionWrapper({ it.assertResult(*values) })
fun <T> terminate(): tom<T> = assertionWrapper({ it.assertTerminated() })
fun <T> valueCount(count: Int): tom<T> = assertionWrapper({ it.assertValueCount(count) })
fun <T> valueSequence(sequence: Iterable<T>): tom<T> = assertionWrapper({ it.assertValueSequence(sequence) })
fun <T> valueSet(expected: Collection<T>): tom<T> = assertionWrapper({ it.assertValueSet(expected) })
fun <T> valueOnly(vararg values: T): tom<T> = assertionWrapper({ it.assertValuesOnly(*values) })

