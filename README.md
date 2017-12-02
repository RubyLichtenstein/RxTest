# RxKotlinTest

[![Build Status](https://travis-ci.org/RubyLichtenstein/RxKotlinTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxKotlinTest)
[![codecov](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest/branch/master/graph/badge.svg)](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest)

## More readable tests 
```kotlin
Observable.just("Hello")
    .test {
        it shouldEmit "Hello"
        it should complete()
        it shouldHave noErrors()
    }
```

## Extendable API
```kotlin
Maybe.just("Hello")
     .test{
        it shouldEmit noValues()
     }
```
## Test Building blocks

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

```kotlin
TestObserver should Matcher<TestObserver<T>>
TestObserver shouldHave Matcher<TestObserver<T>>
TestObserver shouldBe Matcher<TestObserver<T>>
TestObserver shouldEmit Matcher<TestObserver<T>>)
TestObserver shouldEmit T
TestObserver shouldEmit (T) -> Boolean
TestObserver shouldNeverEmit T
TestObserver shouldNeverEmit (T) -> Boolean
```
### Extensions 
```kotlin
Maybe.test{

}
Single.test{

}
Observable.test{

}
Completable.test{

}

Flowable.test{

}
```
 
# Create Matcher

#### 1. From scratch 
Using: `matcher(action: (TestObserver<T>) -> Boolean, message: String): TestObserverMatcher<T>`

```kotlin
fun <T> moreValuesThen(count: Int) = matcher<T>({ it.values().size > count },
                                                "Should have more values then $count")
```
```kotlin
fun <T> lessValuesThen(count: Int) = matcher<T>({ it.values().size < count }, 
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

