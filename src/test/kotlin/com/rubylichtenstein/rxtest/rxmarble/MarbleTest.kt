package com.rubylichtenstein.rxtest.rxmarble

import com.rubylichtenstein.rxtest.assertions.shouldEmit
import com.rubylichtenstein.rxtest.assertions.shouldHave
import com.rubylichtenstein.rxtest.assertions.shouldNeverEmit
import com.rubylichtenstein.rxtest.extentions.test
import com.rubylichtenstein.rxtest.matchers.*
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import junit.framework.AssertionFailedError
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class MarbleTest : Spek({
    val scheduler = TestScheduler()
    val tick = 15L
    fun advanceTime(intervals: Int) {
        scheduler.advanceTimeBy(intervals * tick, TimeUnit.MILLISECONDS)
    }

    describe("marble") {

        it("use a marble of indexes and a mapping list of any type") {
            rxmarble(
                    "--1---3-|", scheduler, tick,
                    listOf(2, 4, 9)
            ).test {
                advanceTime(10)
                it shouldEmit values(2, 9)
            }

            rxmarble(
                    "--1-2-3-|", scheduler, tick,
                    listOf("hello", "world", "!")
            ).test {
                advanceTime(10)
                it shouldEmit values("hello", "world", "!")
            }


            rxmarble("--1-2--3--|", scheduler, tick,
                    listOf(true, false, true)
            ).test {
                advanceTime(10)
                it shouldEmit values(true, false, true)
                it shouldHave complete()

            }
        }

        it("can does the mapping with a lambda") {
            rxmarble(
                    "--1-6-8--", scheduler, tick,
                    { index : Int -> "Got: $index" }
            ).test {
                advanceTime(10)
                it shouldEmit values("Got: 1", "Got: 6", "Got: 8")
                it shouldHave notComplete()
            }

        }


        on("---1--2--4---|") {
            val observable: Observable<Int> = rxmarble(
                    "---1--2--4---|", scheduler, tick,
                    listOf(2, 4, 9, 16) // values emitted for the indexes 1, 2 and 4
            )
            observable.test {
                it("starts empty") {
                    it shouldEmit values()
                    it shouldHave notComplete()
                }

                it("should then emits 2") {
                    advanceTime(4)
                    it shouldEmit values(2)
                    it shouldHave notComplete()
                    it shouldHave noErrors()
                }

                it("should then emit 4, 16, |") {
                    advanceTime(10)
                    it shouldEmit values(2, 4, 16)
                    it shouldHave noErrors()
                    it shouldHave complete()
                }



            }


        }




        it("emits errors for ---#") {
            rxmarble(
                    "---#", scheduler, tick, listOf(42)
            ).test {
                advanceTime(10)
                it shouldHave failure(RxMarble.MarbleError::class.java)
            }
        }
    }



    describe("invalid marbles") {
        val scheduler = TestScheduler()
        val ints = (1..12).map { it * it }

        it("has indices starting at 1, not 0") {
            val message = illegalArgument {
                rxmarble("--0--", scheduler, mapping = ints)
            }
            assertTrue { message.contains("[0]"); message.contains("not found") }

        }

        it("rejects invalid indices") {
            val message = illegalArgument {
                rxmarble("--1--13", scheduler, mapping = ints)
            }
            assertTrue { message.contains("[13]"); message.contains("not found") }
        }

        it("should not accept letters") {
            // contrary to the javascript marble, we don't accept letters
            val message = illegalArgument {
                rxmarble("--1-2-a---", scheduler, mapping = ints)
            }
            assertTrue { message.contains("invalid characters") }
        }

        it("should not have multiple subscriptions") {
            val message = illegalArgument {
                rxmarble("--^-^-1---", scheduler, mapping = ints)
            }
            assertTrue { message.contains("multiple subscriptions") }
        }

        it("should not have multiple terminal events") {
            val invalids = listOf("-#-|", "--||", "--#-#")
            for (marble in invalids) {
                val message = illegalArgument {
                    rxmarble(marble, scheduler, mapping = ints)
                }
                assertTrue { message.contains("multiple terminal events") }
            }

        }

        it("tests that the lambda function would not crash") {
            illegalArgument {
                val list = listOf(1, 2)
                rxmarble(
                        "--1--2-4---", scheduler, tick,
                        { list[it] }
                )
            }
        }

    }
})


fun illegalArgument(function: () -> Unit): String {
    val e = try {
        function.invoke()
        null
    } catch (e: Throwable) {
        e
    }
    when (e) {
        null -> throw AssertionFailedError("Was expected to fail with IllegalArgumentException, got accepted instead")
        is IllegalArgumentException -> return e.message ?: ""
        else -> throw AssertionFailedError("Was expected to fail with IllegalArgumentException, throwed instead $e")
    }

}