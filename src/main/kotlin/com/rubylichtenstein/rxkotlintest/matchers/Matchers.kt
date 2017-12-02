package com.rubylichtenstein.rxkotlintest.matchers

import io.reactivex.observers.BaseTestConsumer
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber

/**
 * Created by ruby on 27/10/17.
 */


/**
 * @see io.reactivex.observers.TestObserver.assertComplete
 */
//fun <T> baseComplete() = AssertionToMatcher<T, TestObserver<T>>({ it.assertComplete() }, "Complete")
fun <T, U :BaseTestConsumer<T, U>> complete()
        = AssertionToMatcher<T, U>({ it.assertComplete() }, "Complete")

fun <T> notComplete() = AssertionToMatcher<T, TestObserver<T>>({ it.assertNotComplete() }, "NotComplete")
fun <T> error(error: Throwable) = AssertionToMatcher<T, TestObserver<T>>({ it.assertError(error) }, "Error $error")
fun <T> error(errorClass: Class<out Throwable>) = AssertionToMatcher<T, TestObserver<T>>({ it.assertError(errorClass) })
fun <T> error(errorPredicate: (Throwable) -> Boolean) = AssertionToMatcher<T, TestObserver<T>>({ it.assertError(errorPredicate) })
fun <T> noErrors() = AssertionToMatcher<T, TestObserver<T>>({ it.assertNoErrors() })
fun <T, U :BaseTestConsumer<T, U>> value(value: T) = AssertionToMatcher<T, U>({ it.assertValue(value) })
fun <T, U :BaseTestConsumer<T, U>> value(valuePredicate: (T) -> Boolean) = AssertionToMatcher<T, U>({ it.assertValue(valuePredicate) })
fun <T, U :BaseTestConsumer<T, U>> never(value: T) = AssertionToMatcher<T, U>({ it.assertNever(value) })
fun <T, U :BaseTestConsumer<T, U>> never(neverPredicate: (T) -> Boolean) = AssertionToMatcher<T, U>({ it.assertNever(neverPredicate) })

fun <T> valueAt(index: Int, value: T) = AssertionToMatcher<T, TestObserver<T>>({ it.assertValueAt(index, value) })

fun <T> valueAt(index: Int, valuePredicate: (T) -> Boolean) = AssertionToMatcher<T, TestObserver<T>>({ it.assertValueAt(index, valuePredicate) })

fun <T> values(vararg values: T) = AssertionToMatcher<T, TestObserver<T>>({ it.assertValues(*values) })

fun <T> empty() = AssertionToMatcher<T, TestObserver<T>>({ it.assertEmpty() })

//fun <T> noTimeout() = AssertionToMatcher<T>({ it.assertNoTimeout() })
//
//fun <T> timeout() = AssertionToMatcher<T>({ it.assertTimeout() })

fun <T> subscribed() = AssertionToMatcher<T, TestObserver<T>>({ it.assertSubscribed() })

//fun <T> notSubscribed() = AssertionToMatcher<T>({ it.assertNotSubscribed() })

fun <T> failure(errorPredicate: (Throwable) -> Boolean, vararg values: T) = AssertionToMatcher<T, TestObserver<T>>({ it.assertFailure(errorPredicate, values) })

fun <T> failure(error: Class<out Throwable>, vararg values: T) = AssertionToMatcher<T, TestObserver<T>>({ it.assertFailure(error, *values) })

fun <T> failureAndMessage(error: Class<out Throwable>,
                          message: String,
                          vararg values: T) = AssertionToMatcher<T, TestObserver<T>>({ it.assertFailureAndMessage(error, message, *values) })

fun <T> result(vararg values: T) = AssertionToMatcher<T, TestObserver<T>>({ it.assertResult(*values) })

fun <T> terminate() = AssertionToMatcher<T, TestObserver<T>>({ it.assertTerminated() })

fun <T> valueCount(count: Int) = AssertionToMatcher<T, TestObserver<T>>({ it.assertValueCount(count) })

fun <T> valueSequence(sequence: Iterable<T>) = AssertionToMatcher<T, TestObserver<T>>({ it.assertValueSequence(sequence) })

fun <T> valueSet(expected: Collection<T>) = AssertionToMatcher<T, TestObserver<T>>({ it.assertValueSet(expected) })
fun <T> valueOnly(vararg values: T) = AssertionToMatcher<T, TestObserver<T>>({ it.assertValuesOnly(*values) })

