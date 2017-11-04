package com.rubylichtenstein
import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.Result
import io.kotlintest.matchers.should
import io.reactivex.Observable
import io.reactivex.observers.TestObserver

/**
 * Created by ruby on 27/10/17.
 */

fun <T> Observable<T>.test(action: TestObserver<T>.() -> Unit): TestObserver<T> {
    val testObserver = test()
    testObserver.action()
    return testObserver
}

fun <T> Observable<T>.testIt(action: (TestObserver<T>) -> Unit): TestObserver<T> {
    val testObserver = test()
    action(testObserver)
    return testObserver
}

fun <T> test(action: (TestObserver<T>) -> Unit): Matcher<TestObserver<T>> {
    return object : Matcher<TestObserver<T>> {
        override fun test(value: TestObserver<T>) = try {
            action(value)
            Result(true, "")
        } catch (assertionError: AssertionError) {
            Result(false, assertionError.message.toString())

        }
    }
}

fun <T> complete(): Matcher<TestObserver<T>> = test({ it.assertComplete() })
fun <T> notComplete(): Matcher<TestObserver<T>> = test({ it.assertNotComplete() })
fun <T> error(error: Throwable): Matcher<TestObserver<T>> = test({ it.assertError(error) })
fun <T> error(errorClass: Class<out Throwable>): Matcher<TestObserver<T>> = test({ it.assertError(errorClass) })
fun <T> error(errorPredicate: (Throwable) -> Boolean): Matcher<TestObserver<T>> = test({ it.assertError(errorPredicate) })
fun <T> noErrors(): Matcher<TestObserver<T>> = test({ it.assertNoErrors() })
fun <T> value(value: T): Matcher<TestObserver<T>> = test({ it.assertValue(value) })
fun <T> value(valuePredicate: (T) -> Boolean): Matcher<TestObserver<T>> = test({ it.assertValue(valuePredicate) })
fun <T> never(value: T): Matcher<TestObserver<T>> = test({ it.assertNever(value) })
fun <T> never(neverPredicate: (T) -> Boolean): Matcher<TestObserver<T>> = test({ it.assertNever(neverPredicate) })
fun <T> valueAt(index: Int, value: T): Matcher<TestObserver<T>> = test({ it.assertValueAt(index, value) })
fun <T> valueAt(index: Int, valuePredicate: (T) -> Boolean): Matcher<TestObserver<T>> = test({ it.assertValueAt(index, valuePredicate) })
fun <T> values(vararg values: T): Matcher<TestObserver<T>> = test({ it.assertValues(*values) })
fun <T> empty(): Matcher<TestObserver<T>> = test({
    it.assertSubscribed()
            .assertNoValues()
            .assertNoErrors()
            .assertNotComplete()
})

fun <T> noTimeout(): Matcher<TestObserver<T>> = test({ it.assertNoTimeout() })
fun <T> timeout(): Matcher<TestObserver<T>> = test({ it.assertTimeout() })
fun <T> subscribed(): Matcher<TestObserver<T>> = test({ it.assertSubscribed() })
fun <T> notSubscribed(): Matcher<TestObserver<T>> = test({ it.assertNotSubscribed() })

fun <T> failure(errorPredicate: (Throwable) -> Boolean, vararg values: T): Matcher<TestObserver<T>>
        = test({ it.assertFailure(errorPredicate, values) })

fun <T> failure(error: Class<out Throwable>, vararg values: T): Matcher<TestObserver<T>>
        = test({ it.assertFailure(error, *values) })

fun <T> failureAndMessage(error: Class<out Throwable>,
                          message: String,
                          vararg values: T): Matcher<TestObserver<T>> = test(
        { it.assertFailureAndMessage(error, message, *values) })

fun <T> result(vararg values: T): Matcher<TestObserver<T>> = test({ it.assertResult(*values) })
fun <T> terminate(): Matcher<TestObserver<T>> = test({ it.assertTerminated() })
fun <T> valueCount(count: Int): Matcher<TestObserver<T>> = test({ it.assertValueCount(count) })
fun <T> valueSequence(sequence: Iterable<T>): Matcher<TestObserver<T>> = test({ it.assertValueSequence(sequence) })
fun <T> valueSet(expected: Collection<T>): Matcher<TestObserver<T>> = test({ it.assertValueSet(expected) })
fun <T> valueOnly(vararg values: T): Matcher<TestObserver<T>> = test({ it.assertValuesOnly(*values) })

infix fun <T> T.shouldBe(matcher: Matcher<T>) = should(matcher)
