package com.rubylichtenstein.rxkotlintest

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldHave
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test

class CoreTest {

    fun <T> noValues() = valueCount<T>(0)

    fun <T> errorOrComplete(error: Throwable) = error<T>(error) or complete()

    fun <T> moreValuesThen(count: Int)
            = compose<T>({ it.values().size > count }, "Should have more values then $count")

    fun <T> lessValuesThen(count: Int)
            = compose<T>({ it.values().size < count }, "Should have less values then $count")

    fun <T> valueCountBetween(min: Int, max: Int) = moreValuesThen<T>(min) and lessValuesThen<T>(max)

    @Test
    fun composeTest() {
        val values = listOf<String>("Rx", "Kotlin", "Test")
        Observable.fromIterable(values)
                .test {
                    it shouldHave moreValuesThen(2)
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

        Observable.just("","")
                .test {
                    it shouldHave valueCountBetween(1, 3)
                }
    }

    @Test
    fun assertionWrapperTest() {
        val detailMessage = "error message"

        val assertionWrapperPass
                = assertionWrapper<String> {}

        val assertionWrapper = assertionWrapper<String> {
            throw AssertionError(detailMessage)
        }

        assertionWrapperPass.test(TestObserver()).passed shouldBe true
        assertionWrapperPass.test(TestObserver()).message shouldBe passedMessage


        assertionWrapper.test(TestObserver()).passed shouldBe false
        assertionWrapper.test(TestObserver()).message shouldBe detailMessage
    }
}

