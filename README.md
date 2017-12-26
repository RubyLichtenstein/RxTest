# RxTest &middot; [![Build Status](https://travis-ci.org/RubyLichtenstein/RxTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxTest) [![codecov](https://codecov.io/gh/RubyLichtenstein/RxTest/branch/master/graph/badge.svg)](https://codecov.io/gh/RubyLichtenstein/RxTest) [![Download](https://api.bintray.com/packages/rubylichtenstein/RxTest/com.rubylichtenstein.rxtest/images/download.svg)](https://bintray.com/rubylichtenstein/RxTest/com.rubylichtenstein.rxtest/_latestVersion) [![Kotlin version badge](https://img.shields.io/badge/kotlin-1.2.10-blue.svg)](http://kotlinlang.org/) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

RxTest is a Kotlin library for testing RxJava.

* **Clear tests:**
* **Easy to extend:**
* **Simple to use:**

- ### Clear tests
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

## Components

 - [Matchers](https://github.com/RubyLichtenstein/RxTest/wiki/Matchers)
 - [Assertions](https://github.com/RubyLichtenstein/RxTest/wiki/Assertions)
 - [Extensions](https://github.com/RubyLichtenstein/RxTest/wiki/Extensions)    

```kotlin
<extension> <assertion> <matcher(value)>
testObserver should complete()
testObserver shouldEmit value()
``` 
 
## [Create Matcher](https://github.com/RubyLichtenstein/RxTest/wiki/Create-matcher)

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


