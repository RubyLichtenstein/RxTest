# RxTest 
__[Kotlin DSL](https://kotlinlang.org/docs/reference/type-safe-builders.html) for testing RxJava. inspired by [KotlinTest](https://github.com/kotlintest/kotlintest).__

[![Build Status](https://travis-ci.org/RubyLichtenstein/RxTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxTest)
[![codecov](https://codecov.io/gh/RubyLichtenstein/RxTest/branch/master/graph/badge.svg)](https://codecov.io/gh/RubyLichtenstein/RxTest)
[ ![Download](https://api.bintray.com/packages/rubylichtenstein/RxTest/com.rubylichtenstein.rxtest/images/download.svg) ](https://bintray.com/rubylichtenstein/RxTest/com.rubylichtenstein.rxtest/_latestVersion)[![Kotlin version badge](https://img.shields.io/badge/kotlin-1.2.10-blue.svg)](http://kotlinlang.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

## Key Features
- ### More readable tests
```kotlin
import com.rubylichtenstein.rxtest.assertions.*
import com.rubylichtenstein.rxtest.extentions.*

Observable.just("Hello RxTest!")
    .test {
        it shouldEmit "Hello RxTest!"
        it should complete()
        it shouldHave noErrors()
    }
```

- ### Extendable - Easily create custom matcher
```kotlin
import com.rubylichtenstein.rxtest.matchers.*

fun <T, U : BaseTestConsumer<T, U>> noValues() = valueCount<T, U>(0)

Maybe.just("Hello!")
     .test{
        it shouldEmit noValues()
     }
```

## RxTest Building blocks

 - Matchers
 - Assertions
 - Extensions    

```kotlin
<extension> <assertion> <matcher(value)>
testObserver should complete()
testObserver shouldEmit value()
``` 
### Matchers

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

### Assertions

- `should <Matcher>`
- `shouldHave <Matcher>`
- `shouldBe <Matcher>`
- `shouldEmit <Matcher>`
- `shouldEmit <T>`
- `shouldEmit <(T) -> Boolean>`
- `shouldNeverEmit <T>`
- `shouldNeverEmit <(T) -> Boolean>`

### Extensions
#### Write test code inside test block 

```kotlin
Maybe.test{

}
```

```kotlin
Single.test{

}
```

```kotlin
Observable.test{

}
```

```kotlin
Completable.test{

}
```

```kotlin
Flowable.test{

}
```
 
## Create Matcher

#### 1. From scratch
With help of createMatcher()

```kotlin
import com.rubylichtenstein.rxtest.matchers.*

fun <T, U : BaseTestConsumer<T, U>> createMatcher(assertion: (BaseTestConsumer<T, U>) -> Boolean,      
                                                  message: String)
                                                  : Matcher<BaseTestConsumer<T, U>>
```
Examples:
- Assert emitted more values than X
```kotlin
import com.rubylichtenstein.rxtest.matchers.*

fun <T, U : BaseTestConsumer<T, U>> moreValuesThen(count: Int)
    = createMatcher<T, U>({ it.values().size > count },
                          failMessage = "Emited Less values then $count")
                          
Observable.just("Hello", "RxTest")
        .test {
            it shouldEmit moreValuesThen(1)
        }                          
```
- Assert emitted less values than X
```kotlin
import com.rubylichtenstein.rxtest.matchers.*

fun <T, U : BaseTestConsumer<T, U>> lessValuesThen(count: Int)
    = createMatcher<T, U>({ it.values().size < count },
                          failMessage = "More values then $count")    
                          
Observable.just("Hello", "RxTest")
        .test {
            it shouldEmit lessValuesThen(3)
        }                             
```

#### 2. Wrap existing
```kotlin
fun <T, U : BaseTestConsumer<T, U>> noValues() = valueCount<T, U>(0)
```

#### 3. Combine with And/Or
```kotlin
import com.rubylichtenstein.rxtest.matchers.*

fun <T, U : BaseTestConsumer<T, U>> errorOrComplete(error: Throwable)
            = error<T, U>(error) or complete<T, U>()
```
```kotlin
import com.rubylichtenstein.rxtest.matchers.*

fun <T, U : BaseTestConsumer<T, U>> valueCountBetween(min: Int, max: Int) 
            = moreValuesThen<T, U>(min) and lessValuesThen<T, U>(max)

```

## Download
- Gradle
```groovy
testCompile 'com.rubylichtenstein:rxtest:1.0.3'
```

- Maven
```xml
<dependency>
    <groupId>com.rubylichtenstein</groupId>
    <artifactId>rxtest</artifactId>
    <version>1.0.3</version>
    <type>pom</type>
</dependency>
```
## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/RubyLichtenstein/RxTest/issues).

## Contribution 

More than welcome :).


