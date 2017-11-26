# RxKotlinTest

[![Build Status](https://travis-ci.org/RubyLichtenstein/RxKotlinTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxKotlinTest)
[![codecov](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest/branch/master/graph/badge.svg)](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest)

# Introduction

RxKotlinTest help you write more readable tests for RxJava2.

The project have 3 parts
1. Hamcrest extension for testObserver assertions
2. Assertion methods for more readable tests
3. Extension function for observables    

Hamcrest Matcher's let you write
`assertThat(testObserver, complete())`
more readable `testObserver.should(complete())`
kotiln infix `testObserver should complete()`
### Matcher's
- `complete()`
- `error(error: Throwable)`
- `value(t: T)` 

[full list]
```kotlin
assertThat(testObserver, complete())
```
or
```kotlin
Observable.just("HelloRxKotlinTest")
    .test {
        it shouldEmit "HelloRxKotlinTest"
        it should complete()
        it shouldHave noErrors()
    }
```
### Assertions
```kotlin
TestObserver<T>.should(matcher: Matcher<TestObserver<T>>)
TestObserver<T>.shouldHave(matcher: Matcher<TestObserver<T>>) 
TestObserver<T>.shouldBe(matcher: Matcher<TestObserver<T>>)
TestObserver<T>.shouldEmit(matcher: Matcher<TestObserver<T>>) 
TestObserver<T>.shouldEmit(t: T) = shouldHave(value(t))
TestObserver<T>.shouldEmit(t: (T) -> Boolean) = shouldHave(value(t))
TestObserver<T>.shouldNeverEmit(t: T) = shouldHave(never(t))
TestObserver<T>.shouldNeverEmit(t: (T) -> Boolean) = shouldHave(never(t))
```
### RxExtensions 
```kotlin
Maybe<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T>
Single<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T>
Observable<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T>
Completable.test(action: (TestObserver<Void>) -> Unit): TestObserver<Void>
```
### Create matcher
```kotlin

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

