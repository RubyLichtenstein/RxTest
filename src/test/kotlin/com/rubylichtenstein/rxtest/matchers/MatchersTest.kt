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


        on("values") {
            val valueSequence = listOf(item0, item1, item2)
            val obs = Observable.fromIterable(valueSequence)

            it("valueSequenceTest") {
                obs.test {
                    it shouldEmit valueSequence(valueSequence)
                }
            }

            it("valueSetTest") {
                obs.test {
                    it shouldEmit valueSet(valueSequence)
                }
            }

            it("value Only") {
                ReplaySubject.create<String>()
                        .also {
                            it.onNext(item0)
                            it.onNext(item1)
                            it.onNext(item2)
                        }
                        .test {
                            it shouldEmit valueOnly(item0, item1, item2)
                        }
            }
        }

        on("never") {
            val value = "a"
            val never = "b"
            val obs = Observable.just(value)

            val valuePredicate = Predicate { v: String -> v == never }

            it("should never") {
                obs.test {
                    it should never(never)
                }
            }

            it("should never emit") {
                obs.test {
                    it shouldNeverEmit never
                }
            }
            it("should never predicate") {
                obs.test {
                    it should never(valuePredicate)
                }
            }

            it("should never") {
                obs.test {
                    it shouldNeverEmit valuePredicate
                }
            }
        }


        it("emptyTest") {
            PublishSubject.create<String>()
                    .test {
                        it shouldBe empty()
                    }
        }

        on("failure") {
            val value0 = "a"
            val value1 = "b"
            val errorMessage = "Error"
            val error = Throwable(errorMessage)

            val obs = ReplaySubject.create<String>()
                    .also {
                        it.onNext(value0)
                        it.onNext(value1)
                        it.onError(error)
                    }

            it("failure0Test") {


                obs.test {
                    it shouldHave failure(Throwable::class.java, value0, value1)
                }
            }

            it("failure1Test") {
                obs.test {
                    it.assertFailure(Predicate { true }, value0, value1)
                    it shouldHave failure(Predicate { true }, value0, value1)
                }
            }

            it("failureAndMessageTest") {

                obs.test {
                    it.assertFailureAndMessage(Throwable::class.java, errorMessage, value0, value1)
                    it shouldHave failureAndMessage(Throwable::class.java, errorMessage, value0, value1)
                }
            }
        }

        it("resultTest") {
            val value0 = "a"
            val value1 = "b"
            val values = listOf(value0, value1)
            Observable.just(values)
                    .test {
                        it shouldHave result(values)
                    }
        }

        it("subscribeTest") {
            val value0 = "a"
            Observable.just(value0)
                    .apply {
                        subscribe({ })
                        test {
                            it should subscribed()
                        }
                    }
        }

        it("valueCountTest") {
            Observable.fromIterable(items)
                    .test {
                        it shouldHave valueCount(items.size)
                    }
        }

        it("terminateTest") {
            Observable.just(item0)
                    .test {
                        it should terminate()
                    }

            Observable.error<String>(Throwable())
                    .test {
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