# RxTest
<img src="./art/rx_logo.png" width="100"><img src="./art/plus_icon.png"><img src="http://hamcrest.org/images/logo.jpg" width="100"><img src="./art/plus_icon.png"><img src="./art/kotlin_logo.png" width="100">

[![Build Status](https://travis-ci.org/RubyLichtenstein/RxTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxKotlinTest)
[![codecov](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest/branch/master/graph/badge.svg)](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest)
[![Download](https://api.bintray.com/packages/rubylichtenstein/RxKotlinTest/com.rubylichtenstein.rxkotlintest/images/download.svg?version=1.0.0) ](https://bintray.com/rubylichtenstein/RxKotlinTest/com.rubylichtenstein.rxkotlintest/1.0.0/link)
## More readable RxJava2 tests 
```kotlin
import com.rubylichtenstein.rxkotlintest.assertions.*
import com.rubylichtenstein.rxkotlintest.extentions.*

Observable.just("Hello RxKotTe!")
    .test {
        it shouldEmit "Hello RxKotTe!"
        it should complete()
        it shouldHave noErrors()
    }
```

## Extendable API
```kotlin
Maybe.just("Hello!")
     .test{
        it shouldEmit noValues()
     }
```
## RxTest Building blocks

 - Matchers (Hamcrest)
 - Assertions
 - Extensions    

```kotlin
<extension> <assertion> <matcher(value)>
testObserver should complete()
testObserver shouldEmit value()
``` 
### Matchers
#### Hamcrest matchers for test consumer assertions

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
#### Clear assertion intent with test consumer extantion functions

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
 
# Create Matcher

#### 1. From scratch 
Using: `createMatcher<T, U : BaseTestConsumer<T, U>>(
                private val assertion: (BaseTestConsumer<T, U>) -> Boolean,
                private val mismatchMessage: String,
                private val matchMessage: String) : TypeSafeMatcher<BaseTestConsumer<T, U>>()`

```kotlin
fun <T, U : BaseTestConsumer<T, U>> moreValuesThen(count: Int)
    = createMatcher<T, U>({ it.values().size > count },
    mismatchMessage = "Less or equal values then $count",
    matchMessage = "More values then $count"
)
```
```kotlin
fun <T, U : BaseTestConsumer<T, U>> lessValuesThen(count: Int)
    = createMatcher<T, U>({ it.values().size < count },
    mismatchMessage = "More or equal values then $count",
    matchMessage = "Less values then $count"
)                                   
```

#### 2. Wrap existing
```kotlin
fun <T, U : BaseTestConsumer<T, U>> noValues() = valueCount<T, U>(0)
```

#### 3. Combine with anyOf and allOf
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
testCompile 'com.rubylichtenstein:rxkotlintest:1.0.0'
```

Maven
```xml
<dependency>
    <groupId>com.rubylichtenstein</groupId>
    <artifactId>rxkotlintest</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

# Contribute

Contact me - ruby.lich@gmail.com

