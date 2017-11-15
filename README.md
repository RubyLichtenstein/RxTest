# RxKotlinTest
## Testing RxJava2 made fun with kotlin DSL

[![Build Status](https://travis-ci.org/RubyLichtenstein/RxKotlinTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxKotlinTest)
[![codecov](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest/branch/master/graph/badge.svg)](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest)

## Exmaple

```kotlin
Observable.just("Hello")
                .test {
                    it shouldHave value("Hello")
                    it should complete()
                    it shouldHave noErrors()
                }
                
Observable.never<Unit>()
                .test {
                    it shouldHave valueCount(0)
                    it should notComplete()
                    it shouldHave noErrors()
                }
```

## Usage
```kotlin

it should complete() 
it should notComplete()
it shouldHave error(error: Throwable)
it shouldHave error(errorClass: Class<out Throwable>)
it shouldHave error(errorPredicate: (Throwable) -> Boolean)
it shouldHave noErrors(): TestObserverMatcher<T>
it shouldHave value(value: T): TestObserverMatcher<T>
it shouldHave value(valuePredicate: (T) -> Boolean)
it shouldHave never(value: T)
it shouldHave never(neverPredicate: (T) -> Boolean)
it shouldHave valueAt(index: Int, value: T)
it shouldHave valueAt(index: Int, valuePredicate: (T) -> Boolean)
it shouldHave values(vararg values: T)
it shouldBy empty()
it shouldHave noTimeout()
it shouldHave timeout()
it should subscribed()
it should notSubscribed()
it shouldHave failure(errorPredicate: (Throwable) -> Boolean, vararg values: T)
it shouldHave failure(error: Class<out Throwable>, vararg values: T)
it shouldHave failureAndMessage(error: Class<out Throwable>, message: String, vararg values: T)
it shouldHave result(vararg values: T)
it should terminate()
it shouldHave valueCount(count: Int)
it shouldHave valueSequence(sequence: Iterable<T>)
it shouldHave valueSet(expected: Collection<T>)
it shouldHave valueOnly(vararg values: T)

```

## Creating your own assertions

```kotlin
fun <T> noValues() = valueCount<T>(0)
fun <T> oneOrTowValues() = valueCount<T>(1) or valueCount(2)

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
                it shouldHave oneOrTowValues()
            }

    Observable.just("","")
            .test {
                it shouldHave valueCountBetween(1, 3)
            }
}
```

## Download
Gradle

    testCompile 'com.rubylichtenstein:rxkotlintest:1.0.2'


## Contributing

Contact me - ruby.lichtenstein@gmail.com

