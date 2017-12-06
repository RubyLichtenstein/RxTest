<img src="./art/rx_logo.png" width="100"><img src="./art/kotlin_logo.png" width="100">

# RxKotTe

[![Build Status](https://travis-ci.org/RubyLichtenstein/RxKotlinTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxKotlinTest)
[![codecov](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest/branch/master/graph/badge.svg)](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest)
[![Download](https://api.bintray.com/packages/rubylichtenstein/RxKotlinTest/com.rubylichtenstein.rxkotlintest/images/download.svg?version=1.2.3) ](https://bintray.com/rubylichtenstein/RxKotlinTest/com.rubylichtenstein.rxkotlintest/1.2.3/link)
## More readable tests 
```kotlin
import com.rubylichtenstein.rxkotlintest.assertions.*
import com.rubylichtenstein.rxkotlintest.extentions.*

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

- `should <Matcher>`
- `shouldHave <Matcher>`
- `shouldBe <Matcher>`
- `shouldEmit <Matcher>`
- `shouldEmit <T>`
- `shouldEmit <(T) -> Boolean>`
- `TestObserver shouldNeverEmit <T>`
- `shouldNeverEmit <(T) -> Boolean>`

### Extensions 
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
 
# Create Matcher

#### 1. From scratch 
Using: `class CreateMatcher<T, U : BaseTestConsumer<T, U>>(
                private val assertion: (BaseTestConsumer<T, U>) -> Boolean,
                private val mismatchMessage: String,
                private val matchMessage: String) : TypeSafeMatcher<BaseTestConsumer<T, U>>()`

```kotlin
fun <T, U : BaseTestConsumer<T, U>> moreValuesThen(count: Int)
    = CreateMatcher<T, U>({ it.values().size > count },
    mismatchMessage = "Should have more values then $count",
    matchMessage = "Have more values then $count"
)
```
```kotlin
fun <T, U : BaseTestConsumer<T, U>> lessValuesThen(count: Int)
    = CreateMatcher<T, U>({ it.values().size < count },
    mismatchMessage = "Should have less values then $count",
    matchMessage = "Have less values then $count"
)                                   
```

#### 2. Wrap existing
```kotlin
fun <T, U : BaseTestConsumer<T, U>> noValues() = valueCount<T, U>(0)
```

#### 3. Combine with OR and AND
```kotlin
fun <T, U : BaseTestConsumer<T, U>> errorOrComplete(error: Throwable)
            = anyOf(error<T, U>(error), complete())
```
```kotlin
fun <T, U : BaseTestConsumer<T, U>> valueCountBetween(min: Int, max: Int) = allOf(moreValuesThen<T, U>(min), lessValuesThen<T, U>(max))

```

# Download
Gradle
```groovy
testCompile 'com.rubylichtenstein:rxkotlintest:1.2.3'
```

Maven
```xml
<dependency>
    <groupId>com.rubylichtenstein</groupId>
    <artifactId>rxkotlintest</artifactId>
    <version>1.2.3</version>
    <type>pom</type>
</dependency>
```

# Contribute

Contact me - ruby.lich@gmail.com

