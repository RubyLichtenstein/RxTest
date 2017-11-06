package com.rubylichtenstein.rxkotlintest

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldHave
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test

class CoreTest {

    fun <T> moreValuesThen(count: Int)
            = compose<T>({ it.values().size > count },
            "Should have more values then $count")

    @Test
    fun composeTest() {
        Observable.just("Hello", "Hello", "Hello")
                .test {
                    it shouldHave moreValuesThen(2)
                    it shouldHave noErrors()
                    it shouldHave valueSequence(listOf("Hello", "Hello", "Hello"))
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