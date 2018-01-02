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
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreateMatcherTest {


    fun <T, U : BaseTestConsumer<T, U>> noValues()
            = valueCount<T, U>(0)

    fun <T, U : BaseTestConsumer<T, U>> errorOrComplete(error: Throwable)
            = error<T, U>(error) or complete()

    fun <T, U : BaseTestConsumer<T, U>> moreValuesThen(count: Int)
            = createMatcher<T, U>({ it.values().size > count },
            failMessage = "Less values then $count"
    )

    fun <T, U : BaseTestConsumer<T, U>> lessValuesThen(count: Int)
            = createMatcher<T, U>({ it.values().size < count },
            failMessage = "More values then $count"
    )

    fun <T, U : BaseTestConsumer<T, U>> valueCountBetween(min: Int, max: Int) =
            moreValuesThen<T, U>(min) and lessValuesThen<T, U>(max)

    @Test
    fun createMatcherOrAndTest() {
        Observable.just("", "")
                .test {
                    it shouldHave (valueCountBetween<String, TestObserver<String>>(0, 1) or values("", ""))
                    it shouldHave (valueCountBetween<String, TestObserver<String>>(1, 3) and values("", ""))
                    try {
                        it shouldHave (valueCountBetween<String, TestObserver<String>>(0, 1) and values("", ""))
                    } catch (e: AssertionError) {

                    }

                    it shouldHave (valueCountBetween<String, TestObserver<String>>(0, 3) or values("", ""))
                }
    }

    @Test
    fun createMatcherTest() {

        val values = listOf<String>("Rx", "Kotlin", "Test")
        Observable.fromIterable(values)
                .test {
                    it shouldEmit moreValuesThen(2)
                    it shouldHave noErrors()
                    it shouldHave valueSequence(values)
                }

        Observable.empty<String>()
                .test {
                    it shouldHave noValues()
                }

        Observable.just("")
                .test {
                    it shouldHave errorOrComplete(Throwable())
                }

        Observable.just("", "")
                .test {
                    it shouldHave valueCountBetween(1, 3)
                }
    }

    @Test
    fun createFailTest() {
        try {
            Observable.just("", "")
                    .test {
                        it shouldHave moreValuesThen(45)
                    }
        } catch (e: Throwable) {
            assertThat(e, notNullValue())
        }
    }

    @Test
    fun failTest() {
        try {
            Observable.just("")
                    .test {
                        it should notComplete()
                    }
        } catch (e: Throwable) {
            assertThat(e, notNullValue())
        }
    }
}

