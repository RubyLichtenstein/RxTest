package com.rubylichtenstein.rxkotlintest.matchers

import com.rubylichtenstein.rxkotlintest.assertions.shouldHave
import com.rubylichtenstein.rxkotlintest.extentions.test
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anyOf
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreateMatcherTest {

    fun <T> noValues() = valueCount<T>(0)

    fun <T> errorOrComplete(error: Throwable) = anyOf(error<T>(error), complete())

    fun <T> moreValuesThen(count: Int)
            = CreateMatcher<T>({ it.values().size > count },
            "Should have more values then $count")

    fun <T> lessValuesThen(count: Int)
            = CreateMatcher<T>({ it.values().size < count },
            "Should have less values then $count")

    fun <T> valueCountBetween(min: Int, max: Int) = allOf(moreValuesThen<T>(min), lessValuesThen<T>(max))

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
//        val detailMessage = "error message"
//
//        val assertionWrapperPass
//                = assertionToMatcher<String> {}
//
//        val assertionWrapper = assertionToMatcher<String> {
//            throw AssertionError(detailMessage)
//        }
//
//        assertionWrapperPass.test(TestObserver()).passed shouldBe true
//        assertionWrapperPass.test(TestObserver()).message shouldBe passedMessage
//
//
//        assertionWrapper.test(TestObserver()).passed shouldBe false
//        assertionWrapper.test(TestObserver()).message shouldBe detailMessage
    }
}

