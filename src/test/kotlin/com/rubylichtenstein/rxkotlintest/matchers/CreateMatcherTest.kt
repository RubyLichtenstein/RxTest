package com.rubylichtenstein.rxkotlintest.matchers

import com.rubylichtenstein.rxkotlintest.assertions.should
import com.rubylichtenstein.rxkotlintest.assertions.shouldHave
import com.rubylichtenstein.rxkotlintest.extentions.test
import io.reactivex.Observable
import io.reactivex.observers.BaseTestConsumer
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.TypeSafeMatcher
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreateMatcherTest {

    fun <T, U : BaseTestConsumer<T, U>> noValues() = valueCount<T, U>(0)

    fun <T, U : BaseTestConsumer<T, U>> errorOrComplete(error: Throwable)
            = error<T, U>(error) or complete()

    fun <T, U : BaseTestConsumer<T, U>> moreValuesThen(count: Int)
            = createMatcher<T, U>({ it.values().size > count },
            message = "Less or equal values then $count"
    )

    fun <T, U : BaseTestConsumer<T, U>> lessValuesThen(count: Int)
            = createMatcher<T, U>({ it.values().size < count },
            message = "More or equal values then $count"
    )

    fun <T, U : BaseTestConsumer<T, U>> valueCountBetween(min: Int, max: Int) =
            moreValuesThen<T, U>(min) and lessValuesThen(max)

//    @Test
//    fun createMatcherTest() {
//        val count = 4;
//        val mismatchMessage = "More or equal values then $count"
//        val matchMessage = "Less values then $count"
//
//        val matcher = createMatcher<String, TestObserver<String>>({ it.values().size < count },
//                mismatchMessage = mismatchMessage,
//                matchMessage = matchMessage
//        )
//
//        matcher.
//    }

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

