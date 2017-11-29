package com.rubylichtenstein.rxkotlintest.matchers

/**
 * Created by ruby on 27/10/17.
 */


/**
 * @see io.reactivex.observers.TestObserver.assertComplete
 */
fun <T> complete() = AssertionToMatcher<T>({ it.assertComplete() }, "Complete")

fun <T> notComplete() = AssertionToMatcher<T>({ it.assertNotComplete() }, "NotComplete")
fun <T> error(error: Throwable) = AssertionToMatcher<T>({ it.assertError(error) }, "Error $error")
fun <T> error(errorClass: Class<out Throwable>) = AssertionToMatcher<T>({ it.assertError(errorClass) })
fun <T> error(errorPredicate: (Throwable) -> Boolean) = AssertionToMatcher<T>({ it.assertError(errorPredicate) })
fun <T> noErrors() = AssertionToMatcher<T>({ it.assertNoErrors() })
fun <T> value(value: T) = AssertionToMatcher<T>({ it.assertValue(value) })
fun <T> value(valuePredicate: (T) -> Boolean) = AssertionToMatcher<T>({ it.assertValue(valuePredicate) })
fun <T> never(value: T) = AssertionToMatcher<T>({ it.assertNever(value) })
fun <T> never(neverPredicate: (T) -> Boolean) = AssertionToMatcher<T>({ it.assertNever(neverPredicate) })

fun <T> valueAt(index: Int, value: T) = AssertionToMatcher<T>({ it.assertValueAt(index, value) })

fun <T> valueAt(index: Int, valuePredicate: (T) -> Boolean) = AssertionToMatcher<T>({ it.assertValueAt(index, valuePredicate) })

fun <T> values(vararg values: T) = AssertionToMatcher<T>({ it.assertValues(*values) })

fun <T> empty() = AssertionToMatcher<T>({ it.assertEmpty() })

fun <T> noTimeout() = AssertionToMatcher<T>({ it.assertNoTimeout() })

fun <T> timeout() = AssertionToMatcher<T>({ it.assertTimeout() })

fun <T> subscribed() = AssertionToMatcher<T>({ it.assertSubscribed() })

fun <T> notSubscribed() = AssertionToMatcher<T>({ it.assertNotSubscribed() })

fun <T> failure(errorPredicate: (Throwable) -> Boolean, vararg values: T) = AssertionToMatcher<T>({ it.assertFailure(errorPredicate, values) })

fun <T> failure(error: Class<out Throwable>, vararg values: T) = AssertionToMatcher<T>({ it.assertFailure(error, *values) })

fun <T> failureAndMessage(error: Class<out Throwable>,
                          message: String,
                          vararg values: T) = AssertionToMatcher<T>({ it.assertFailureAndMessage(error, message, *values) })

fun <T> result(vararg values: T) = AssertionToMatcher<T>({ it.assertResult(*values) })

fun <T> terminate() = AssertionToMatcher<T>({ it.assertTerminated() })

fun <T> valueCount(count: Int) = AssertionToMatcher<T>({ it.assertValueCount(count) })

fun <T> valueSequence(sequence: Iterable<T>) = AssertionToMatcher<T>({ it.assertValueSequence(sequence) })

fun <T> valueSet(expected: Collection<T>) = AssertionToMatcher<T>({ it.assertValueSet(expected) })
fun <T> valueOnly(vararg values: T) = AssertionToMatcher<T>({ it.assertValuesOnly(*values) })

