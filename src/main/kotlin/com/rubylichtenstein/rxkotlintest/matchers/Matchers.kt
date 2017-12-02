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
//fun <T, U :BaseTestConsumer<T, U>> baseComplete() = AssertionToMatcher<T, U>({ it.assertComplete() }, "Complete")
fun <T, U :BaseTestConsumer<T, U>> complete()
        = AssertionToMatcher<T, U>({ it.assertComplete() }, "Complete")

fun <T, U :BaseTestConsumer<T, U>> notComplete() = AssertionToMatcher<T, U>({ it.assertNotComplete() }, "NotComplete")
fun <T, U :BaseTestConsumer<T, U>> error(error: Throwable) = AssertionToMatcher<T, U>({ it.assertError(error) }, "Error $error")
fun <T, U :BaseTestConsumer<T, U>> error(errorClass: Class<out Throwable>) = AssertionToMatcher<T, U>({ it.assertError(errorClass) })
fun <T, U :BaseTestConsumer<T, U>> error(errorPredicate: (Throwable) -> Boolean) = AssertionToMatcher<T, U>({ it.assertError(errorPredicate) })
fun <T, U :BaseTestConsumer<T, U>> noErrors() = AssertionToMatcher<T, U>({ it.assertNoErrors() })
fun <T, U :BaseTestConsumer<T, U>> value(value: T) = AssertionToMatcher<T, U>({ it.assertValue(value) })
fun <T, U :BaseTestConsumer<T, U>> value(valuePredicate: (T) -> Boolean) = AssertionToMatcher<T, U>({ it.assertValue(valuePredicate) })
fun <T, U :BaseTestConsumer<T, U>> never(value: T) = AssertionToMatcher<T, U>({ it.assertNever(value) })
fun <T, U :BaseTestConsumer<T, U>> never(neverPredicate: (T) -> Boolean) = AssertionToMatcher<T, U>({ it.assertNever(neverPredicate) })

fun <T, U :BaseTestConsumer<T, U>> valueAt(index: Int, value: T) = AssertionToMatcher<T, U>({ it.assertValueAt(index, value) })

fun <T, U :BaseTestConsumer<T, U>> valueAt(index: Int, valuePredicate: (T) -> Boolean) = AssertionToMatcher<T, U>({ it.assertValueAt(index, valuePredicate) })

fun <T, U :BaseTestConsumer<T, U>> values(vararg values: T) = AssertionToMatcher<T, U>({ it.assertValues(*values) })

fun <T, U :BaseTestConsumer<T, U>> empty() = AssertionToMatcher<T, U>({ it.assertEmpty() })


fun <T, U :BaseTestConsumer<T, U>> subscribed() = AssertionToMatcher<T, U>({ it.assertSubscribed() })


fun <T, U :BaseTestConsumer<T, U>> failure(errorPredicate: (Throwable) -> Boolean, vararg values: T) = AssertionToMatcher<T, U>({ it.assertFailure(errorPredicate, values) })

fun <T, U :BaseTestConsumer<T, U>> failure(error: Class<out Throwable>, vararg values: T) = AssertionToMatcher<T, U>({ it.assertFailure(error, *values) })

fun <T, U :BaseTestConsumer<T, U>> failureAndMessage(error: Class<out Throwable>,
                          message: String,
                          vararg values: T) = AssertionToMatcher<T, U>({ it.assertFailureAndMessage(error, message, *values) })

fun <T, U :BaseTestConsumer<T, U>> result(vararg values: T) = AssertionToMatcher<T, U>({ it.assertResult(*values) })

fun <T, U :BaseTestConsumer<T, U>> terminate() = AssertionToMatcher<T, U>({ it.assertTerminated() })

fun <T, U :BaseTestConsumer<T, U>> valueCount(count: Int) = AssertionToMatcher<T, U>({ it.assertValueCount(count) })

fun <T, U :BaseTestConsumer<T, U>> valueSequence(sequence: Iterable<T>) = AssertionToMatcher<T, U>({ it.assertValueSequence(sequence) })

fun <T, U :BaseTestConsumer<T, U>> valueSet(expected: Collection<T>) = AssertionToMatcher<T, U>({ it.assertValueSet(expected) })
fun <T, U :BaseTestConsumer<T, U>> valueOnly(vararg values: T) = AssertionToMatcher<T, U>({ it.assertValuesOnly(*values) })

//fun <T, U :BaseTestConsumer<T, U>> noTimeout() = AssertionToMatcher<T, U :BaseTestConsumer<T, U>>({ it.assertNoTimeout() })
//
//fun <T, U :BaseTestConsumer<T, U>> notSubscribed() = AssertionToMatcher<T, U :BaseTestConsumer<T, U>>({ it.assertNotSubscribed() })
//fun <T, U :BaseTestConsumer<T, U>> timeout() = AssertionToMatcher<T, U :BaseTestConsumer<T, U>>({ it.assertTimeout() })
