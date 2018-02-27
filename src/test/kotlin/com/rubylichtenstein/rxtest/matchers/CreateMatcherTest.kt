package com.rubylichtenstein.rxtest.matchers

import com.rubylichtenstein.rxtest.assertions.should
import com.rubylichtenstein.rxtest.assertions.shouldEmit
import com.rubylichtenstein.rxtest.assertions.shouldHave
import com.rubylichtenstein.rxtest.extentions.test
import io.reactivex.Observable
import io.reactivex.observers.BaseTestConsumer
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object CreateMatcherTest : Spek({

    describe("MatchersTest") {

        on("createMatcherOrAndTest") {
            val hello = "Hello"
            val stranger = "stranger"

            val itemsCount = 2

            val obs by memoized { Observable.just(hello, stranger) }

            it("or test") {
                obs.test {
                    it shouldHave (valueCountBetween<String>(0, itemsCount-1) or values(hello, stranger))
                }
            }

            it("and test") {
                obs.test {
                    it shouldHave (valueCountBetween<String>(itemsCount-1, itemsCount+1) and values(hello, stranger))
                }
            }

            it("fail and test") {
                obs.test {
                    try {
                        it shouldHave (valueCountBetween<String>(0, itemsCount-1) and values(hello, stranger))
                    } catch (e: AssertionError) {
                        assertThat(e, notNullValue())
                    }
                }
            }

            it("or test") {
                obs.test {
                    it shouldHave (valueCountBetween<String>(0, itemsCount+1) or values(hello, stranger))
                }
            }

        }
        on("fail test") {
            it("") {
                try {
                    Observable.just("")
                        .test {
                            it should notComplete()
                        }
                } catch (e: Throwable) {
                    assertThat(e, notNullValue())
                }
            }

            it("fail test") {
                try {
                    Observable.just("", "")
                        .test {
                            it shouldHave moreValuesThen(45)
                        }
                } catch (e: Throwable) {
                    assertThat(e, notNullValue())
                }
            }

        }
        on("create matcher test") {
            val values = listOf<String>("Rx", "Kotlin", "Test")

            it("moreValuesThen") {
                Observable.fromIterable(values)
                    .test {
                        it shouldEmit moreValuesThen(2)
                    }
            }

            it("noValues") {
                Observable.empty<String>()
                    .test {
                        it shouldHave noValues()
                    }
            }

            it("errorOrComplete") {
                Observable.just("")
                    .test {
                        it shouldHave errorOrComplete(Throwable())
                    }
            }

            it("valueCountBetween") {
                Observable.just("", "")
                    .test {
                        it shouldHave valueCountBetween(1, 3)
                    }
            }

        }
    }
})

internal fun <T, U : BaseTestConsumer<T, U>> noValues() = valueCount<T, U>(0)

internal fun <T, U : BaseTestConsumer<T, U>> errorOrComplete(error: Throwable) = error<T, U>(error) or complete()

internal fun <T, U : BaseTestConsumer<T, U>> moreValuesThen(count: Int) = createMatcher<T, U>(
    { it.values().size > count },
    failMessage = "Less values then $count"
)


internal fun <T, U : BaseTestConsumer<T, U>> lessValuesThen(count: Int) =
    createMatcher<T, U>(
        { it.values().size < count },
        failMessage = "More values then $count"
    )

internal fun <T> valueCountBetween(min: Int, max: Int) =
    moreValuesThen<T, TestObserver<T>>(min) and lessValuesThen<T, TestObserver<T>>(max)

