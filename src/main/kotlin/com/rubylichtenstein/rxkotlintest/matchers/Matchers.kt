package com.rubylichtenstein.rxkotlintest.matchers

import com.rubylichtenstein.rxkotlintest.core.AssertionToMatcher

/**
 * Created by ruby on 27/10/17.
 */


/**
 * @see io.reactivex.observers.TestObserver.assertComplete
 */
fun <T> complete() = AssertionToMatcher<T>({ it.assertComplete() }, "Should Complete")
fun <T> notComplete() = AssertionToMatcher<T>({ it.assertNotComplete() }, "Should NotComplete")
fun <T> error(error: Throwable) = AssertionToMatcher<T>({ it.assertError(error) }, "Error $error")

fun <T> error(errorClass: Class<out Throwable>) = AssertionToMatcher<T>({ it.assertError(errorClass) })

fun <T> error(errorPredicate: (Throwable) -> Boolean): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertError(errorPredicate) })

fun <T> noErrors(): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertNoErrors() })

fun <T> value(value: T): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertValue(value) })

fun <T> value(valuePredicate: (T) -> Boolean): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertValue(valuePredicate) })

fun <T> never(value: T): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertNever(value) })

fun <T> never(neverPredicate: (T) -> Boolean): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertNever(neverPredicate) })

fun <T> valueAt(index: Int, value: T): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertValueAt(index, value) })

fun <T> valueAt(index: Int, valuePredicate: (T) -> Boolean): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertValueAt(index, valuePredicate) })

fun <T> values(vararg values: T): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertValues(*values) })

fun <T> empty(): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertEmpty() })

fun <T> noTimeout(): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertNoTimeout() })

fun <T> timeout(): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertTimeout() })

fun <T> subscribed(): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertSubscribed() })

fun <T> notSubscribed(): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertNotSubscribed() })

fun <T> failure(errorPredicate: (Throwable) -> Boolean, vararg values: T): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertFailure(errorPredicate, values) })

fun <T> failure(error: Class<out Throwable>, vararg values: T): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertFailure(error, *values) })

fun <T> failureAndMessage(error: Class<out Throwable>,
                          message: String,
                          vararg values: T): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertFailureAndMessage(error, message, *values) })

fun <T> result(vararg values: T): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertResult(*values) })

fun <T> terminate(): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertTerminated() })

fun <T> valueCount(count: Int): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertValueCount(count) })

fun <T> valueSequence(sequence: Iterable<T>): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertValueSequence(sequence) })

fun <T> valueSet(expected: Collection<T>): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertValueSet(expected) })

fun <T> valueOnly(vararg values: T): AssertionToMatcher<T>
        = AssertionToMatcher({ it.assertValuesOnly(*values) })

