package com.rubylichtenstein.rxkotlintest.matchers

import com.rubylichtenstein.rxkotlintest.core.TestObserverMatcher
import com.rubylichtenstein.rxkotlintest.core.TestObserverMatcherDep
import com.rubylichtenstein.rxkotlintest.core.assertionToMatcher

/**
 * Created by ruby on 27/10/17.
 */


/**
 * @see io.reactivex.observers.TestObserver.assertComplete
 */
fun <T> complete(): TestObserverMatcher<T>
        = TestObserverMatcher({ it.assertComplete() })

fun <T> notComplete(): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertNotComplete() })

fun <T> error(error: Throwable): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertError(error) })

fun <T> error(errorClass: Class<out Throwable>): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertError(errorClass) })

fun <T> error(errorPredicate: (Throwable) -> Boolean): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertError(errorPredicate) })

fun <T> noErrors(): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertNoErrors() })

fun <T> value(value: T): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertValue(value) })

fun <T> value(valuePredicate: (T) -> Boolean): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertValue(valuePredicate) })

fun <T> never(value: T): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertNever(value) })

fun <T> never(neverPredicate: (T) -> Boolean): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertNever(neverPredicate) })

fun <T> valueAt(index: Int, value: T): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertValueAt(index, value) })

fun <T> valueAt(index: Int, valuePredicate: (T) -> Boolean): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertValueAt(index, valuePredicate) })

fun <T> values(vararg values: T): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertValues(*values) })

fun <T> empty(): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertEmpty() })

fun <T> noTimeout(): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertNoTimeout() })

fun <T> timeout(): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertTimeout() })

fun <T> subscribed(): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertSubscribed() })

fun <T> notSubscribed(): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertNotSubscribed() })

fun <T> failure(errorPredicate: (Throwable) -> Boolean, vararg values: T): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertFailure(errorPredicate, values) })

fun <T> failure(error: Class<out Throwable>, vararg values: T): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertFailure(error, *values) })

fun <T> failureAndMessage(error: Class<out Throwable>,
                          message: String,
                          vararg values: T): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertFailureAndMessage(error, message, *values) })

fun <T> result(vararg values: T): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertResult(*values) })

fun <T> terminate(): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertTerminated() })

fun <T> valueCount(count: Int): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertValueCount(count) })

fun <T> valueSequence(sequence: Iterable<T>): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertValueSequence(sequence) })

fun <T> valueSet(expected: Collection<T>): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertValueSet(expected) })

fun <T> valueOnly(vararg values: T): TestObserverMatcherDep<T>
        = assertionToMatcher({ it.assertValuesOnly(*values) })

