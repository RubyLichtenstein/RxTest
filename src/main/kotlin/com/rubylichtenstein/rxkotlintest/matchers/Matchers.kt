package com.rubylichtenstein.rxkotlintest.matchers

import io.reactivex.observers.BaseTestConsumer

/**
 * Created by ruby on 27/10/17.
 */


/**
 * @see io.reactivex.observers.TestObserver.assertComplete
 */
fun <T, U :BaseTestConsumer<T, U>> complete()
        = createMatcher<T, U>({ it.assertComplete() }, "Complete")

fun <T, U :BaseTestConsumer<T, U>> notComplete()
        = createMatcher<T, U>({ it.assertNotComplete() }, "Not complete")

fun <T, U :BaseTestConsumer<T, U>> error(error: Throwable)
        = createMatcher<T, U>({ it.assertError(error) }, "Error $error")

fun <T, U :BaseTestConsumer<T, U>> error(errorClass: Class<out Throwable>)
        = createMatcher<T, U>({ it.assertError(errorClass) }, "Error $errorClass")

fun <T, U :BaseTestConsumer<T, U>> error(errorPredicate: (Throwable) -> Boolean)
        = createMatcher<T, U>({ it.assertError(errorPredicate) }, "Error $errorPredicate")

fun <T, U :BaseTestConsumer<T, U>> noErrors()
        = createMatcher<T, U>({ it.assertNoErrors() }, "No errors")

fun <T, U :BaseTestConsumer<T, U>> value(value: T)
        = createMatcher<T, U>({ it.assertValue(value) }, "Value $value")

fun <T, U :BaseTestConsumer<T, U>> value(valuePredicate: (T) -> Boolean)
        = createMatcher<T, U>({ it.assertValue(valuePredicate) })

fun <T, U :BaseTestConsumer<T, U>> never(value: T)
        = createMatcher<T, U>({ it.assertNever(value) })

fun <T, U :BaseTestConsumer<T, U>> never(neverPredicate: (T) -> Boolean) = createMatcher<T, U>({ it.assertNever(neverPredicate) })
fun <T, U :BaseTestConsumer<T, U>> valueAt(index: Int, value: T) = createMatcher<T, U>({ it.assertValueAt(index, value) })
fun <T, U :BaseTestConsumer<T, U>> valueAt(index: Int, valuePredicate: (T) -> Boolean) = createMatcher<T, U>({ it.assertValueAt(index, valuePredicate) })
fun <T, U :BaseTestConsumer<T, U>> values(vararg values: T) = createMatcher<T, U>({ it.assertValues(*values) })
fun <T, U :BaseTestConsumer<T, U>> empty() = createMatcher<T, U>({ it.assertEmpty() })
fun <T, U :BaseTestConsumer<T, U>> subscribed() = createMatcher<T, U>({ it.assertSubscribed() })
fun <T, U :BaseTestConsumer<T, U>> failure(errorPredicate: (Throwable) -> Boolean, vararg values: T) = createMatcher<T, U>({ it.assertFailure(errorPredicate, values) })
fun <T, U :BaseTestConsumer<T, U>> failure(error: Class<out Throwable>, vararg values: T) = createMatcher<T, U>({ it.assertFailure(error, *values) })
fun <T, U :BaseTestConsumer<T, U>> failureAndMessage(error: Class<out Throwable>,
                          message: String,
                          vararg values: T) = createMatcher<T, U>({ it.assertFailureAndMessage(error, message, *values) })
fun <T, U :BaseTestConsumer<T, U>> result(vararg values: T) = createMatcher<T, U>({ it.assertResult(*values) })
fun <T, U :BaseTestConsumer<T, U>> terminate() = createMatcher<T, U>({ it.assertTerminated() })
fun <T, U :BaseTestConsumer<T, U>> valueCount(count: Int) = createMatcher<T, U>({ it.assertValueCount(count) })
fun <T, U :BaseTestConsumer<T, U>> valueSequence(sequence: Iterable<T>) = createMatcher<T, U>({ it.assertValueSequence(sequence) })
fun <T, U :BaseTestConsumer<T, U>> valueSet(expected: Collection<T>) = createMatcher<T, U>({ it.assertValueSet(expected) })
fun <T, U :BaseTestConsumer<T, U>> valueOnly(vararg values: T) = createMatcher<T, U>({ it.assertValuesOnly(*values) })

//fun <T, U :BaseTestConsumer<T, U>> noTimeout() = createMatcher<T, U :BaseTestConsumer<T, U>>({ it.assertNoTimeout() })
//fun <T, U :BaseTestConsumer<T, U>> notSubscribed() = createMatcher<T, U :BaseTestConsumer<T, U>>({ it.assertNotSubscribed() })
//fun <T, U :BaseTestConsumer<T, U>> timeout() = createMatcher<T, U :BaseTestConsumer<T, U>>({ it.assertTimeout() })
