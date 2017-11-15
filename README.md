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

## Download
Gradle

## Contributing


