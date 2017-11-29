package com.rubylichtenstein.rxkotlintest.matchers

import com.rubylichtenstein.rxkotlintest.assertions.*
import com.rubylichtenstein.rxkotlintest.extentions.test
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import org.junit.Test
import java.util.concurrent.TimeUnit

class MatchersTest {

    val item0 = "a"
    val item1 = "a"
    val item2 = "a"

    val items = listOf(item0, item1, item2)

    @Test
    fun completeTest() {
        Observable.just(item0)
                .test {
                    it.assertComplete()
                    it should complete()
                }

        Maybe.just(item0)
                .test {
                    it.assertComplete()
                    it should complete()
                }

        Single.just(item0)
                .test {
                    it.assertComplete()
                    it should complete()
                }

        Completable.complete()
                .test {
                    it.assertComplete()
                    it should complete()
                }
    }

    @Test
    fun notCompleteTest() {
        PublishSubject.create<String>()
                .apply {
                    onNext("a")
                    onNext("b")
                }
                .test {
                    it.assertNotComplete()
                    it should notComplete()
                }
    }

    @Test
    fun errorTest() {
        val to = TestObserver<String>()
        val publishSubject = PublishSubject.create<String>()
        val assertionError = AssertionError()
        publishSubject.subscribe(to)
        publishSubject.onNext("a")
        publishSubject.onError(assertionError)

        to.assertError(assertionError)
        to shouldHave error(assertionError)
        to.assertError(AssertionError::class.java)
        to shouldHave error(AssertionError::class.java)

        to.assertError({ true })
        to shouldHave error({ true })
    }

    @Test
    fun noErrorTest() {
        val to = TestObserver<String>()
        val publishSubject = PublishSubject.create<String>()
        publishSubject.subscribe(to)
        publishSubject.onNext("a")

        to.assertNoErrors()
        to shouldHave noErrors()
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

        val a = { v: String -> v.length == 1 }
        to.assertValue(a)
        to shouldHave value(a)
        to shouldEmit a
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

        to.assertValueAt(0, { it == value0 })
        to.assertValueAt(1, { it == value1 })

        to shouldHave valueAt(0, { it == value0 })
        to shouldHave valueAt(1, { it == value1 })
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
        val valuePredicate = { v: String -> v.equals(never) }

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
    fun failureTest() {
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
                    it shouldHave failure({ true }, value0, value1)
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

//    @Test
//    fun timeoutTest() {
//        val value0 = "a"
//        val testScheduler = TestScheduler()
//
//
//        Observable.just(value0)
//                .timeout(1, TimeUnit.MILLISECONDS)
//                .observeOn(testScheduler)
//                .doOnNext {
//                    testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS)
//                }
//                .test {
//                    it.assertTimeout()
//                    it shouldHave timeout()
//                }}
}


