# RxKotlinTest

[![Build Status](https://travis-ci.org/RubyLichtenstein/RxKotlinTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxKotlinTest)
[![codecov](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest/branch/master/graph/badge.svg)](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest)

# Introduction

RxKotlinTest is a library build on top of [KotlinTest](https://github.com/kotlintest/kotlintest) to help you write more readable tests for RxJava2.

# Examples
### Basic use
```kotlin
Observable.just("HelloRxKotlinTest")
    .test {
        it shouldEmit "HelloRxKotlinTest"
        it should complete()
        it shouldHave noErrors()
    }
```
### [BDD style](https://github.com/kotlintest/kotlintest/blob/master/doc/reference.md#behavior-spec) 
```kotlin
Given("Value, subject"){
    val value = "HelloRxKotlinTest"
    val subject = PublishSubject.create<String>()
    val subjectTest = subject.test()

    When("subject emit value"){
        subject.onNext(value)

        Then("value emitted"){
            subjectTest shouldEmit value
        }
    }

    When("call subject onComplete"){
        subject.onComplete()

        Then("subject complete with no errors"){
            subjectTest should notComplete()
        }
    }
}    
```
# Usage
### Matcher's
- `complete()`
- `notComplete()`
- `error(error: Throwable)`
- `error(errorClass: Class<out Throwable>)`
- `error(errorPredicate: (Throwable) -> Boolean)`
- `noErrors()`
- `value(t: T)` 
- `value(predicate: (T) -> Boolean)` 
- `values(vararg values: T)` 
- `valueSequence(sequence: Iterable<T>)` 
- `valueSet(set: Collection<T>)` 
- `valueOnly(vararg values: T)` 
- `valueCount(count: Int)` 
- `never(t: T)` 
- `never(predicate: (T) -> Boolean)` 
- `valueAt(index: Int, value: T)` 
- `valueAt(index: Int, valuePredicate: (T) -> Boolean)` 
- `empty()` 
- `timeout()` 
- `noTimeout()` 
- `subscribed()` 
- `notSubscribed()` 
- `failure(errorPredicate: (Throwable) -> Boolean, vararg values: T)` 
- `failure(error: Class<out Throwable>, vararg values: T)` 
- `failureAndMessage(error: Class<out Throwable>, message: String, vararg values: T)` 
- `result(vararg values: T)` 
- `terminate()` 
 
# Create your own Matcher

#### 1. From scratch 
Using: `matcher(action: (TestObserver<T>) -> Boolean, message: String): TestObserverMatcher<T>`

```kotlin
fun <T> moreValuesThen(count: Int) =
    matcher<T>({ it.values().size > count },
    "Should have more values then $count")

fun <T> lessValuesThen(count: Int) =
    matcher<T>({ it.values().size < count }, 
    "Should have less values then $count")
```

#### 2. Wrap existing
```kotlin
fun <T> noValues() = valueCount<T>(0)
```

#### 3. Combine with OR and AND
```kotlin
fun <T> errorOrComplete(error: Throwable) = error<T>(error) or complete()

fun <T> valueCountBetween(min: Int, max: Int) = moreValuesThen<T>(min) and lessValuesThen<T>(max)

@Test
fun composeTest() {
    val values = listOf<String>("Rx", "Kotlin", "Test")
    Observable.fromIterable(values)
            .test {
                it shouldEmit moreValuesThen(2)
                it shouldHave noErrors()
                it shouldEmit valueSequence(values)
            }

    Observable.empty<String>()
            .test {
                it shouldEmit noValues()
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
```
# Download
Gradle
```groovy
testCompile 'com.rubylichtenstein:rxkotlintest:1.0.2'
```

Maven
```xml
<dependency>
    <groupId>com.rubylichtenstein</groupId>
    <artifactId>rxkotlintest</artifactId>
    <version>1.0.2</version>
    <type>pom</type>
</dependency>
```

# Contribute

Contact me - ruby.lich@gmail.com

