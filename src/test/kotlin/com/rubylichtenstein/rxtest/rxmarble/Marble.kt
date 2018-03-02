package com.rubylichtenstein.rxtest.rxmarble


import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit


const val TICK = 15L

fun <T> rxmarble(marbles: String, scheduler: Scheduler, tickMs: Long = TICK, operation: (Int) -> T): Observable<T> {
    val rxm = RxMarble(marbles, true, tickMs, 100L, scheduler)
    rxm.checkMarbles(marbles)
    val mapping: List<T> = try {
        val numbers = 1..(rxm.parseNumbers(marbles).max() ?: 1)
        numbers.map(operation)
    } catch (e: Throwable) {
        throw IllegalArgumentException("Marble [$marbles] : mapping function throwed $e")
    }
    return rxm.create().map { mapping[it - 1] }
}

fun <T> rxmarble(marbles: String, scheduler: Scheduler, tickMs: Long = TICK, mapping: List<T>): Observable<T> {
    val rxm = RxMarble(marbles, true, tickMs, 100L, scheduler)
    rxm.checkMarbles(marbles)
    val invalidNumbers = rxm.parseNumbers(marbles).filter { it !in 1..mapping.size }
    require(invalidNumbers.isEmpty()) { "Marble [$marbles]: indexes $invalidNumbers not found in list $mapping" }
    return rxm.create().map { mapping[it - 1] }
}


fun TestScheduler.advanceByFrame(nb: Int, tickMs: Long = TICK): TestScheduler {
    this.advanceTimeBy(tickMs * nb, TimeUnit.MILLISECONDS)
    return this
}


class RxMarble(val marbles: String, val hot: Boolean, val tickMs: Long, val max: Long, val scheduler: Scheduler) {


    internal fun checkMarbles(marbles: String, hot: Boolean = false) {
        val invalidChars = marbles.filterNot { it in NUMBERS || it in OTHERS }
        require(invalidChars.isEmpty()) {
            "Marble: [$marbles] contains invalid characters [$invalidChars]"
        }
        require(marbles.filter { it == '^' }.count() <= 1) {
            "Marble [$marbles]  contains multiple subscriptions"
        }
        require(marbles.filter { it == '#' || it == '|' }.count() <= 1) {
            "Marble [$marbles] contains multiple terminal events"
        }
        require(hot || !marbles.contains('^')) {
            "Marble [$marbles] is cold but contains a subscription point"
        }
    }

    internal fun parseNumbers(marbles: String): List<Int> {
        return marbles.split(*OTHERS).mapNotNull { it.toIntOrNull() }.distinct().sorted()
    }

    internal fun firstFrame(): Long {
        val first = if (hot) marbles.indexOfFirst { it == '^' } else -1
        return if (first == -1) 0L else first.toLong()
    }

    internal fun lastFrame(): Long {
        val end = marbles.indexOfFirst { it == '#' || it == '|' }
        return if (end == -1) max else end.toLong()
    }

    internal fun completion(): Observable<Int> {
        val end = marbles.firstOrNull { it == '#' || it == '|' }
        return when (end) {
            null -> Observable.never()
            '#' -> Observable.error(MarbleError)
            '|' -> Observable.empty()
            else -> TODO("invalid completion ${completion()}")
        }
    }

    fun create(): Observable<Int> {
        return Observable.merge(Observable.interval(tickMs, TimeUnit.MILLISECONDS, scheduler)
                .take(lastFrame())
                .skip(firstFrame())
                .map { l -> marbles.getOrNull(l.toInt()) ?: "-" }
                .filter { it in NUMBERS }
                .map { "$it".toInt() },
                completion())
    }

    companion object {
        private val NUMBERS = '0'..'9'
        private val OTHERS = charArrayOf('|', '^', '#', '-', '(', ')')
    }

    object MarbleError : RuntimeException("Marble emitted an error")
}

