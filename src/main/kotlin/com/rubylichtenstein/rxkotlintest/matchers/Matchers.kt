package com.rubylichtenstein.rxkotlintest.matchers

import io.reactivex.functions.Predicate
import io.reactivex.observers.BaseTestConsumer

/**
 * Created by ruby on 27/10/17.
 */


/**
 * @see io.reactivex.observers.TestObserver.assertComplete
 */
fun <T, U :BaseTestConsumer<T, U>> complete()
        = createMatcher<T, U>({ it.assertComplete() })

/**
 * @see io.reactivex.observers.TestObserver.assertNotComplete
 */
fun <T, U :BaseTestConsumer<T, U>> notComplete()
        = createMatcher<T, U>({ it.assertNotComplete() })

/**
 * @see io.reactivex.observers.TestObserver.assertError
 */
fun <T, U :BaseTestConsumer<T, U>> error(error: Throwable)
        = createMatcher<T, U>({ it.assertError(error) })

/**
 * @see io.reactivex.observers.TestObserver.assertError
 */
fun <T, U :BaseTestConsumer<T, U>> error(errorClass: Class<out Throwable>)
        = createMatcher<T, U>({ it.assertError(errorClass) })
/**
 * @see io.reactivex.observers.TestObserver.assertError
 */
fun <T, U :BaseTestConsumer<T, U>> error(errorPredicate: (Throwable) -> Boolean)
        = createMatcher<T, U>({ it.assertError(errorPredicate) })

/**
 * @see io.reactivex.observers.TestObserver.assertNoErrors
 */
fun <T, U :BaseTestConsumer<T, U>> noErrors()
        = createMatcher<T, U>({ it.assertNoErrors() })

/**
 * @see io.reactivex.observers.TestObserver.assertValue
 */
fun <T, U :BaseTestConsumer<T, U>> value(value: T)
        = createMatcher<T, U>({ it.assertValue(value) })
/**
 * @see io.reactivex.observers.TestObserver.assertValue
 */
fun <T, U :BaseTestConsumer<T, U>> value(valuePredicate: (T) -> Boolean)
        = createMatcher<T, U>({ it.assertValue(valuePredicate) })
/**
 * @see io.reactivex.observers.TestObserver.assertNever
 */
fun <T, U :BaseTestConsumer<T, U>> never(value: T)
        = createMatcher<T, U>({ it.assertNever(value) })

/**
 * @see io.reactivex.observers.TestObserver.assertNever
 */
fun <T, U :BaseTestConsumer<T, U>> never(neverPredicate: (T) -> Boolean)
        = createMatcher<T, U>({ it.assertNever(neverPredicate) })

/**
 * @see io.reactivex.observers.TestObserver.assertValueAt
 */
fun <T, U :BaseTestConsumer<T, U>> valueAt(index: Int, value: T)
        = createMatcher<T, U>({ it.assertValueAt(index, value) })

/**
 * @see io.reactivex.observers.TestObserver.assertValueAt
 */
fun <T, U :BaseTestConsumer<T, U>> valueAt(index: Int, valuePredicate: Predicate<T>)
        = createMatcher<T, U>({ it.assertValueAt(index, valuePredicate) })

/**
 * @see io.reactivex.observers.TestObserver.assertValueAt
 */
fun <T, U :BaseTestConsumer<T, U>> valueAt(index: Int, valuePredicate: (T) -> Boolean)
        = createMatcher<T, U>({ it.assertValueAt(index, valuePredicate) })

/**
 * @see io.reactivex.observers.TestObserver.assertValues
 */
fun <T, U :BaseTestConsumer<T, U>> values(vararg values: T)
        = createMatcher<T, U>({ it.assertValues(*values) })

/**
 * @see io.reactivex.observers.TestObserver.assertEmpty
 */
fun <T, U :BaseTestConsumer<T, U>> empty()
        = createMatcher<T, U>({ it.assertEmpty() })

/**
 * @see io.reactivex.observers.TestObserver.assertSubscribed
 */
fun <T, U :BaseTestConsumer<T, U>> subscribed()
        = createMatcher<T, U>({ it.assertSubscribed() })

/**
 * @see io.reactivex.observers.TestObserver.assertFailure
 */
fun <T, U :BaseTestConsumer<T, U>> failure(errorPredicate: (Throwable) -> Boolean, vararg values: T)
        = createMatcher<T, U>({ it.assertFailure(errorPredicate, values) })
/**
 * @see io.reactivex.observers.TestObserver.assertFailure
 */
fun <T, U :BaseTestConsumer<T, U>> failure(error: Class<out Throwable>, vararg values: T)
        = createMatcher<T, U>({ it.assertFailure(error, *values) })

/**
 * @see io.reactivex.observers.TestObserver.assertFailureAndMessage
 */
fun <T, U :BaseTestConsumer<T, U>> failureAndMessage(error: Class<out Throwable>,
                          message: String,
                          vararg values: T)
        = createMatcher<T, U>({ it.assertFailureAndMessage(error, message, *values) })

/**
 * @see io.reactivex.observers.TestObserver.assertResult
 */
fun <T, U :BaseTestConsumer<T, U>> result(vararg values: T)
        = createMatcher<T, U>({ it.assertResult(*values) })

/**
 * @see io.reactivex.observers.TestObserver.assertTerminated
 */
fun <T, U :BaseTestConsumer<T, U>> terminate()
        = createMatcher<T, U>({ it.assertTerminated() })

/**
 * @see io.reactivex.observers.TestObserver.assertValueCount
 */
fun <T, U :BaseTestConsumer<T, U>> valueCount(count: Int)
        = createMatcher<T, U>({ it.assertValueCount(count) })

/**
 * @see io.reactivex.observers.TestObserver.assertValueSequence
 */
fun <T, U :BaseTestConsumer<T, U>> valueSequence(sequence: Iterable<T>)
        = createMatcher<T, U>({ it.assertValueSequence(sequence) })

/**
 * @see io.reactivex.observers.TestObserver.assertValueSet
 */
fun <T, U :BaseTestConsumer<T, U>> valueSet(expected: Collection<T>)
        = createMatcher<T, U>({ it.assertValueSet(expected) })

/**
 * @see io.reactivex.observers.TestObserver.assertValuesOnly
 */
fun <T, U :BaseTestConsumer<T, U>> valueOnly(vararg values: T)
        = createMatcher<T, U>({ it.assertValuesOnly(*values) })

//fun <T, U :BaseTestConsumer<T, U>> noTimeout() = createMatcher<T, U :BaseTestConsumer<T, U>>({ it.assertNoTimeout() })
//fun <T, U :BaseTestConsumer<T, U>> notSubscribed() = createMatcher<T, U :BaseTestConsumer<T, U>>({ it.assertNotSubscribed() })
//fun <T, U :BaseTestConsumer<T, U>> timeout() = createMatcher<T, U :BaseTestConsumer<T, U>>({ it.assertTimeout() })
