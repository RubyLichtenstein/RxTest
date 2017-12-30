package com.rubylichtenstein.rxtest.matchers

import com.rubylichtenstein.rxtest.assertions.*
import com.rubylichtenstein.rxtest.extentions.test
import io.reactivex.*
import io.reactivex.functions.Predicate
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import junit.framework.Assert.assertNotNull
import org.junit.Test
import java.util.concurrent.TimeUnit


class MatchersTest {

    val item0 = "a"
    val item1 = "b"
    val item2 = "c"

    val items = listOf(item0, item1, item2)

    fun shouldThrowAssertionError(function: () -> Unit) {
        val assertionError = getAssertionError { function() }
        assertNotNull(assertionError)
    }

    fun getAssertionError(function: () -> Unit): AssertionError? {
        return try {
            function.invoke()
            null
        } catch (assertionError: AssertionError) {
            return assertionError
        }
    }

    @Test
    fun shouldThrowAssertionErrorTest() {

    }

    @Test
    fun completeTest() {
        Maybe.just("")
                .test {
                    it should complete()
                }

        Single.just("")
                .test {
                    it should complete()
                }

        Completable.complete()
                .test {
                    it should complete()
                }

        Observable.just("hello")
                .toFlowable(BackpressureStrategy.BUFFER)
                .test {
                    it should complete()
                }

        shouldThrowAssertionError {
            PublishSubject.create<String>()
                    .test {
                        it should complete()
                    }
        }
    }

    @Test
    fun notCompleteTest() {
        PublishSubject.create<String>()
                .test {
                    it should notComplete()
                }

        shouldThrowAssertionError {
            PublishSubject.create<String>()
                    .test {
                        it should complete()
                    }
        }
    }

    @Test
    fun errorTest1() {
        val assertionError = AssertionError()

        PublishSubject.create<String>()
                .apply {
                    onError(assertionError)
                }
                .test {
                    it shouldHave error(assertionError)
                }
    }

    @Test
    fun errorTest2() {
        val assertionError = AssertionError()

        PublishSubject.create<String>()
                .apply {
                    onError(assertionError)
                }
                .test {
                    it shouldHave error(AssertionError::class.java)
                }
    }

    @Test
    fun errorTest() {
        val assertionError = AssertionError()

        PublishSubject.create<String>()
                .apply {
                    onError(assertionError)
                }
                .test {
                    it shouldHave error(Predicate { it == assertionError })
                }
    }

    @Test
    fun noErrorTest() {
        PublishSubject.create<String>()
                .test {
                    it shouldHave noErrors()
                }
    }

    @Test
    fun valueTest() {
        val to = TestObserver<String>()
        val publishSubject = PublishSubject.create<String>()
        val value = "a"
        publishSubject.subscribe(to)
        publishSubject.onNext(value)

        to.assertValue(value)
        to shouldHave value(value)
        to shouldEmit value

        val valuePredicate = Predicate { v: String -> v.length == 1 }
        to.assertValue(valuePredicate)
        to shouldHave value(valuePredicate)
        to shouldEmit valuePredicate
    }

    @Test
    fun valueAtTest() {
        val to = TestObserver<String>()
        val publishSubject = PublishSubject.create<String>()
        val value0 = "a"
        val value1 = "b"
        publishSubject.subscribe(to)
        publishSubject.onNext(value0)
        publishSubject.onNext(value1)

        to.assertValueAt(0, value0)
        to.assertValueAt(1, value1)

        to shouldHave valueAt(0, value0)
        to shouldHave valueAt(1, value1)

        to.assertValueAt(0, Predicate { value -> value == value0 })
        to.assertValueAt(1, Predicate { value -> value == value1 })

        to shouldHave valueAt(0, Predicate { value -> value == value0 })
        to shouldHave valueAt(1, Predicate { value -> value == value1 })
    }

    @Test
    fun valuesTest() {
        val to = TestObserver<String>()
        val publishSubject = PublishSubject.create<String>()
        val value0 = "a"
        val value1 = "b"
        publishSubject.subscribe(to)
        publishSubject.onNext(value0)
        publishSubject.onNext(value1)

        to.assertValues(value0, value1)
        to shouldHave values(value0, value1)
        to shouldEmit values(value0, value1)
    }


    @Test
    fun valueSequenceTest() {
        val valueSequence = listOf(item0, item1, item2)
        Observable.fromIterable(valueSequence)
                .test {
                    it.assertValueSequence(valueSequence)
                    it shouldEmit valueSequence(valueSequence)
                }
    }

    @Test
    fun valueSetTest() {
        val valueSequence = listOf(item0, item1, item2)
        Observable.fromIterable(valueSequence)
                .test {
                    it.assertValueSet(valueSequence)
                    it shouldEmit valueSet(valueSequence)
                }
    }

    @Test
    fun valueOnly() {
        val replaySubject = ReplaySubject.create<String>()
        replaySubject.onNext(item0)
        replaySubject.onNext(item1)
        replaySubject.onNext(item2)

        replaySubject.test {
            it.assertValuesOnly(item0, item1, item2)
            it shouldEmit valueOnly(item0, item1, item2)
        }
    }

    @Test
    fun neverTest() {
        val to = TestObserver<String>()
        val publishSubject = PublishSubject.create<String>()
        val value = "a"
        val never = "b"
        val valuePredicate = Predicate { v: String -> v.equals(never) }

        publishSubject.subscribe(to)
        publishSubject.onNext(value)

        to.assertNever(never)
        to shouldHave never(never)
        to shouldNeverEmit never

        to.assertNever(valuePredicate)
        to shouldHave never(valuePredicate)
        to shouldNeverEmit valuePredicate
    }

    @Test
    fun emptyTest() {
        PublishSubject.create<String>()
                .apply {
                    subscribe({})
                }
                .test {
                    it.assertEmpty()
                    it shouldBe empty()
                }
    }

    @Test
    fun failure0Test() {
        val value0 = "a"
        val value1 = "b"
        val errorMessage = "Error"
        val error = Throwable(errorMessage)
        ReplaySubject.create<String>()
                .also {
                    it.onNext(value0)
                    it.onNext(value1)
                    it.onError(error)
                }
                .test {
                    it.assertFailure(Throwable::class.java, value0, value1)
                    it shouldHave failure(Throwable::class.java, value0, value1)
                }
    }

    @Test
    fun failure1Test() {
        val value0 = "a"
        val value1 = "b"
        val errorMessage = "Error"
        val error = Throwable(errorMessage)
        ReplaySubject.create<String>()
                .also {
                    it.onNext(value0)
                    it.onNext(value1)
                    it.onError(error)
                }
                .test {
                    it.assertFailure(Predicate { true }, value0, value1)
                    it shouldHave failure(Predicate { true }, value0, value1)
                }
    }

    @Test
    fun failureAndMessageTest() {
        val value0 = "a"
        val value1 = "b"
        val errorMessage = "Error"
        val error = Throwable(errorMessage)
        ReplaySubject.create<String>()
                .also {
                    it.onNext(value0)
                    it.onNext(value1)
                    it.onError(error)
                }
                .test {
                    it.assertFailureAndMessage(Throwable::class.java, errorMessage, value0, value1)
                    it shouldHave failureAndMessage(Throwable::class.java, errorMessage, value0, value1)
                }
    }

    @Test
    fun resultTest() {
        val value0 = "a"
        val value1 = "b"
        val values = listOf(value0, value1)
        Observable.just(values)
                .test {
                    it.assertResult(values)
                    it shouldHave result(values)
                }
    }

    @Test
    fun subscribeTest() {
        val value0 = "a"
        Observable.just(value0).apply {
            subscribe({ })
            test {
                it.assertSubscribed()
                it should subscribed()
            }
        }
    }

    @Test
    fun valueCountTest() {
        Observable.fromIterable(items)
                .test {
                    it.assertValueCount(items.size)
                    it shouldHave valueCount(items.size)
                }
    }

    @Test
    fun terminateTest() {
        Observable.just(item0)
                .test {
                    it.assertTerminated()
                    it should terminate()
                }

        Observable.error<String>(Throwable())
                .test {
                    it.assertTerminated()
                    it should terminate()
                }
    }

    @Test
    fun timeoutTest() {
        val scheduler = TestScheduler()

        Observable
                .interval(100, TimeUnit.MILLISECONDS, scheduler)
                .test {
                    it.await(50, TimeUnit.MILLISECONDS)
                    it shouldHave timeout()
                }
    }

    @Test
    fun noTimeoutTest() {
        val scheduler = TestScheduler()

        Observable
                .interval(100, TimeUnit.MILLISECONDS, scheduler)
                .test {
                    it shouldHave noTimeout()
                }
    }

    @Test
    fun notSubscribedTest() {
        TestObserver<String>()
                .apply {
                    should(notSubscribed())
                }
    }
}


