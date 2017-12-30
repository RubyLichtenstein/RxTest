package com.rubylichtenstein.rxtest.matchers

import com.rubylichtenstein.rxtest.assertions.*
import com.rubylichtenstein.rxtest.extentions.test
import io.reactivex.*
import io.reactivex.functions.Predicate
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import junit.framework.TestCase.assertNotNull
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.concurrent.TimeUnit

class MatchersTest : Spek({
    describe("") {

        val item0 = "a"
        val item1 = "b"
        val item2 = "c"

        val items = listOf(item0, item1, item2)

        it("should complete") {
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



        it("should not complete") {
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

        it("should have error obj") {
            val assertionError = AssertionError()

            PublishSubject.create<String>()
                    .apply {
                        onError(assertionError)
                    }
                    .test {
                        it shouldHave error(assertionError)
                    }
        }

        it("should have error type") {
            PublishSubject.create<String>()
                    .apply {
                        onError(AssertionError())
                    }
                    .test {
                        it shouldHave error(AssertionError::class.java)
                    }
        }

        it("should have error predicate") {
            val assertionError = AssertionError()

            PublishSubject.create<String>()
                    .apply {
                        onError(assertionError)
                    }
                    .test {
                        it shouldHave error(Predicate { it == assertionError })
                    }
        }

        it("should have no errors") {
            PublishSubject.create<String>()
                    .test {
                        it shouldHave noErrors()
                    }
        }

        it("should have first value") {
            val value = "a"

            ReplaySubject.create<String>()
                    .apply {
                        onNext(value)
                    }
                    .test {
                        it shouldHave value(value)
                    }
        }

        it("should emit first value") {
            val value = "a"

            ReplaySubject.create<String>()
                    .apply {
                        onNext(value)
                    }
                    .test {
                        it shouldEmit value
                    }
        }

        on("should have value at") {
            val value0 = "a"
            val value1 = "b"
            val obs by memoized {
                Observable.just(value0, value1)
            }

            it("with obj") {
                obs.test {
                    it shouldHave valueAt(0, value0)
                    it shouldHave valueAt(1, value1)
                }
            }

            it("with predicate") {
                obs.test {
                    it shouldHave valueAt(0, Predicate { value -> value == value0 })
                    it shouldHave valueAt(1, Predicate { value -> value == value1 })
                }
            }
        }

        on("should have values") {
            val value0 = "a"
            val value1 = "b"
            val obs by memoized {
                Observable.just(value0, value1)
            }

            it("have") {
                obs.test {
                    it shouldHave values(value0, value1)
                }
            }

            it("emit") {
                obs.test {
                    it shouldEmit values(value0, value1)

                }
            }
        }


        it("valueSequenceTest") {
            val valueSequence = listOf(item0, item1, item2)
            Observable.fromIterable(valueSequence)
                    .test {
                        it.assertValueSequence(valueSequence)
                        it shouldEmit valueSequence(valueSequence)
                    }
        }

        it("valueSetTest") {
            val valueSequence = listOf(item0, item1, item2)
            Observable.fromIterable(valueSequence)
                    .test {
                        it.assertValueSet(valueSequence)
                        it shouldEmit valueSet(valueSequence)
                    }
        }

        it("value Only") {
            val replaySubject = ReplaySubject.create<String>()
            replaySubject.onNext(item0)
            replaySubject.onNext(item1)
            replaySubject.onNext(item2)

            replaySubject.test {
                it.assertValuesOnly(item0, item1, item2)
                it shouldEmit valueOnly(item0, item1, item2)
            }
        }

        it("neverTest") {
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

        it("emptyTest") {
            PublishSubject.create<String>()
                    .apply {
                        subscribe({})
                    }
                    .test {
                        it.assertEmpty()
                        it shouldBe empty()
                    }
        }

        it("failure0Test") {
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

        it("failure1Test") {
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

        it("failureAndMessageTest") {
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

        it("resultTest") {
            val value0 = "a"
            val value1 = "b"
            val values = listOf(value0, value1)
            Observable.just(values)
                    .test {
                        it.assertResult(values)
                        it shouldHave result(values)
                    }
        }

        it("subscribeTest") {
            val value0 = "a"
            Observable.just(value0).apply {
                subscribe({ })
                test {
                    it.assertSubscribed()
                    it should subscribed()
                }
            }
        }

        it("valueCountTest") {
            Observable.fromIterable(items)
                    .test {
                        it.assertValueCount(items.size)
                        it shouldHave valueCount(items.size)
                    }
        }

        it("terminateTest") {
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

        it("timeoutTest") {
            val scheduler = TestScheduler()

            Observable
                    .interval(100, TimeUnit.MILLISECONDS, scheduler)
                    .test {
                        it.await(50, TimeUnit.MILLISECONDS)
                        it shouldHave timeout()
                    }
        }

        it("noTimeoutTest") {
            val scheduler = TestScheduler()

            Observable
                    .interval(100, TimeUnit.MILLISECONDS, scheduler)
                    .test {
                        it shouldHave noTimeout()
                    }
        }

        it("notSubscribedTest") {
            TestObserver<String>()
                    .apply {
                        should(notSubscribed())
                    }
        }
    }
})

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