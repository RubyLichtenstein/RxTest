package com.rubylichtenstein.rxkotlintest.assertions

import com.rubylichtenstein.rxkotlintest.core.TestObserverMatcher
import com.rubylichtenstein.rxkotlintest.core.assertionWrapper

/**
 * Created by ruby on 27/10/17.
 */


/**
 * @see io.reactivex.observers.TestObserver.assertComplete
 */
fun <T> complete(): TestObserverMatcher<T> =
        assertionWrapper({ it.assertComplete() })

fun <T> notComplete(): TestObserverMatcher<T> =
        assertionWrapper({ it.assertNotComplete() })

fun <T> error(error: Throwable): TestObserverMatcher<T>
        = assertionWrapper({ it.assertError(error) })

fun <T> error(errorClass: Class<out Throwable>): TestObserverMatcher<T>
        = assertionWrapper({ it.assertError(errorClass) })

fun <T> error(errorPredicate: (Throwable) -> Boolean): TestObserverMatcher<T>
        = assertionWrapper({ it.assertError(errorPredicate) })

fun <T> noErrors(): TestObserverMatcher<T>
        = assertionWrapper({ it.assertNoErrors() })

fun <T> value(value: T): TestObserverMatcher<T>
        = assertionWrapper({ it.assertValue(value) })

fun <T> value(valuePredicate: (T) -> Boolean): TestObserverMatcher<T>
        = assertionWrapper({ it.assertValue(valuePredicate) })

fun <T> never(value: T): TestObserverMatcher<T>
        = assertionWrapper({ it.assertNever(value) })

fun <T> never(neverPredicate: (T) -> Boolean): TestObserverMatcher<T>
        = assertionWrapper({ it.assertNever(neverPredicate) })

fun <T> valueAt(index: Int, value: T): TestObserverMatcher<T>
        = assertionWrapper({ it.assertValueAt(index, value) })

fun <T> valueAt(index: Int, valuePredicate: (T) -> Boolean): TestObserverMatcher<T>
        = assertionWrapper({ it.assertValueAt(index, valuePredicate) })

fun <T> values(vararg values: T): TestObserverMatcher<T>
        = assertionWrapper({ it.assertValues(*values) })

fun <T> empty(): TestObserverMatcher<T>
        = assertionWrapper({ it.assertEmpty() })

fun <T> noTimeout(): TestObserverMatcher<T>
        = assertionWrapper({ it.assertNoTimeout() })

fun <T> timeout(): TestObserverMatcher<T>
        = assertionWrapper({ it.assertTimeout() })

fun <T> subscribed(): TestObserverMatcher<T>
        = assertionWrapper({ it.assertSubscribed() })

fun <T> notSubscribed(): TestObserverMatcher<T>
        = assertionWrapper({ it.assertNotSubscribed() })

fun <T> failure(errorPredicate: (Throwable) -> Boolean, vararg values: T): TestObserverMatcher<T>
        = assertionWrapper({ it.assertFailure(errorPredicate, values) })

fun <T> failure(error: Class<out Throwable>, vararg values: T): TestObserverMatcher<T>
        = assertionWrapper({ it.assertFailure(error, *values) })

fun <T> failureAndMessage(error: Class<out Throwable>,
                          message: String,
                          vararg values: T): TestObserverMatcher<T>
        = assertionWrapper({ it.assertFailureAndMessage(error, message, *values) })

fun <T> result(vararg values: T): TestObserverMatcher<T> = assertionWrapper({ it.assertResult(*values) })
fun <T> terminate(): TestObserverMatcher<T> = assertionWrapper({ it.assertTerminated() })
fun <T> valueCount(count: Int): TestObserverMatcher<T> = assertionWrapper({ it.assertValueCount(count) })
fun <T> valueSequence(sequence: Iterable<T>): TestObserverMatcher<T> = assertionWrapper({ it.assertValueSequence(sequence) })
fun <T> valueSet(expected: Collection<T>): TestObserverMatcher<T> = assertionWrapper({ it.assertValueSet(expected) })
fun <T> valueOnly(vararg values: T): TestObserverMatcher<T> = assertionWrapper({ it.assertValuesOnly(*values) })

