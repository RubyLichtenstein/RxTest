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

### Composing Assertions
```kotlin
fun <T> noValues() = valueCount<T>(0)

fun <T> errorOrComplete(error: Throwable) = error<T>(error) or complete()

fun <T> moreValuesThen(count: Int)
        = createAssertion<T>({ it.values().size > count }, "Should have more values then $count")
```

# Usage
### Assertions
```kotlin
fun assertions(){

it should complete() 

it should notComplete()

it shouldHave error(error: Throwable)

it shouldHave error(errorClass: Class<out Throwable>)

it shouldHave error(errorPredicate: (Throwable) -> Boolean)

it shouldHave noErrors()

it shouldEmit T

it shouldEmit (T) -> Boolean

it shouldEmit values(vararg values: T)

it shouldEmit valueSequence(sequence: Iterable<T>)

it shouldEmit Collection<T>

it shouldEmit valueOnly(vararg values: T)

it shouldNeverEmit T

it shouldNeverEmit (T) -> Boolean

it shouldHave valueAt(index: Int, value: T)

it shouldHave valueAt(index: Int, valuePredicate: (T) -> Boolean)

it shouldBe empty()

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

}
```
### Composing assertions
```kotlin
createAssertion(action: (TestObserver<T>) -> Boolean, message: String)
```

# Create your own assertions

### Example
```kotlin
fun <T> noValues() = valueCount<T>(0)

fun <T> errorOrComplete(error: Throwable) = error<T>(error) or complete()

fun <T> moreValuesThen(count: Int)
        = createAssertion<T>({ it.values().size > count }, "Should have more values then $count")

fun <T> lessValuesThen(count: Int)
        = createAssertion<T>({ it.values().size < count }, "Should have less values then $count")

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

Contact me - ruby.lichtenstein@gmail.com

