package com.rubylichtenstein.rxkotlintest.matchers


import com.rubylichtenstein.rxkotlintest.assertions.should
import com.rubylichtenstein.rxkotlintest.assertions.shouldHave
import com.rubylichtenstein.rxkotlintest.extentions.test
import io.reactivex.Observable
import io.reactivex.observers.BaseTestConsumer
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreateMatcherTest {

    fun <T, U : BaseTestConsumer<T, U>> noValues() = valueCount<T, U>(0)

    fun <T, U : BaseTestConsumer<T, U>> errorOrComplete(error: Throwable)
            = anyOf(error<T, U>(error), complete())

    fun <T, U : BaseTestConsumer<T, U>> moreValuesThen(count: Int)
            = CreateMatcher<T, U>({ it.values().size > count },
            "Should have more values then $count",
            "Have more values then $count"
    )

    fun <T, U : BaseTestConsumer<T, U>> lessValuesThen(count: Int)
            = CreateMatcher<T, U>({ it.values().size < count },
            "Should have less values then $count",
            "Have less values then $count"
    )

    fun <T, U : BaseTestConsumer<T, U>> valueCountBetween(min: Int, max: Int) = allOf(moreValuesThen<T, U>(min), lessValuesThen<T, U>(max))

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

