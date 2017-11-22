package com.rubylichtenstein.rxkotlintest.matchers

import com.rubylichtenstein.rxkotlintest.core.TestObserverMatcher
import com.rubylichtenstein.rxkotlintest.core.assertionToMatcher

/**
 * Created by ruby on 27/10/17.
 */


/**
 * @see io.reactivex.observers.TestObserver.assertComplete
 */
fun <T> complete(): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertComplete() })

fun <T> notComplete(): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertNotComplete() })

fun <T> error(error: Throwable): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertError(error) })

fun <T> error(errorClass: Class<out Throwable>): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertError(errorClass) })

fun <T> error(errorPredicate: (Throwable) -> Boolean): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertError(errorPredicate) })

fun <T> noErrors(): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertNoErrors() })

fun <T> value(value: T): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertValue(value) })

fun <T> value(valuePredicate: (T) -> Boolean): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertValue(valuePredicate) })

fun <T> never(value: T): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertNever(value) })

fun <T> never(neverPredicate: (T) -> Boolean): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertNever(neverPredicate) })

fun <T> valueAt(index: Int, value: T): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertValueAt(index, value) })

fun <T> valueAt(index: Int, valuePredicate: (T) -> Boolean): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertValueAt(index, valuePredicate) })

fun <T> values(vararg values: T): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertValues(*values) })

fun <T> empty(): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertEmpty() })

fun <T> noTimeout(): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertNoTimeout() })

fun <T> timeout(): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertTimeout() })

fun <T> subscribed(): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertSubscribed() })

fun <T> notSubscribed(): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertNotSubscribed() })

fun <T> failure(errorPredicate: (Throwable) -> Boolean, vararg values: T): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertFailure(errorPredicate, values) })

fun <T> failure(error: Class<out Throwable>, vararg values: T): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertFailure(error, *values) })

fun <T> failureAndMessage(error: Class<out Throwable>,
                          message: String,
                          vararg values: T): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertFailureAndMessage(error, message, *values) })

fun <T> result(vararg values: T): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertResult(*values) })

fun <T> terminate(): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertTerminated() })

fun <T> valueCount(count: Int): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertValueCount(count) })

fun <T> valueSequence(sequence: Iterable<T>): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertValueSequence(sequence) })

fun <T> valueSet(expected: Collection<T>): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertValueSet(expected) })

fun <T> valueOnly(vararg values: T): TestObserverMatcher<T>
        = assertionToMatcher({ it.assertValuesOnly(*values) })

