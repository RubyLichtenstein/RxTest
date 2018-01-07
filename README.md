# RxTest &middot; <img src="https://github.com/RubyLichtenstein/RxTest/blob/master/art/kotlin_logo.png" alt="kotlin-logo" height="28" width="28"> &middot; <img src="https://github.com/RubyLichtenstein/RxTest/blob/master/art/rx_logo.png" alt="rx-logo" height="34" width="34"> &middot;  [![Build Status](https://travis-ci.org/RubyLichtenstein/RxTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxTest) [![codecov](https://codecov.io/gh/RubyLichtenstein/RxTest/branch/master/graph/badge.svg)](https://codecov.io/gh/RubyLichtenstein/RxTest) [![Download](https://api.bintray.com/packages/rubylichtenstein/RxTest/com.rubylichtenstein.rxtest/images/download.svg)](https://bintray.com/rubylichtenstein/RxTest/com.rubylichtenstein.rxtest/_latestVersion) [![Kotlin version badge](https://img.shields.io/badge/kotlin-1.2.10-blue.svg)](http://kotlinlang.org/) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![Gitter chat](https://badges.gitter.im/gitterHQ/gitter.png)](https://gitter.im/RxTest/) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RxTest-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6647)



RxTest is a Kotlin library for testing RxJava.

#### Main features 
* **Clear tests:** write clean, readable and simple tests in scala-test *should* style.
* **Easy to extend:** create custom matchers for complex test cases or just for more readable code.
* **Simple to use:** same api as rxjava assertions you already know. 

## Exemple
```kotlin
Observable.just("Hello RxTest!")
    .test {
        it shouldEmit "Hello RxTest!"
        it should complete()
        it shouldHave noErrors()
    }
```
## Documentation

* [Home](https://github.com/RubyLichtenstein/RxTest/wiki)
* [Introduction](https://github.com/RubyLichtenstein/RxTest/wiki/Introduction)
* [Examples](https://github.com/RubyLichtenstein/RxTest/wiki/Examples)
* Components
  * [Matchers](https://github.com/RubyLichtenstein/RxTest/wiki/Matchers)
  * [Assertions](https://github.com/RubyLichtenstein/RxTest/wiki/Assertions)
  * [Extensions](https://github.com/RubyLichtenstein/RxTest/wiki/Extensions)

* [Custom matchers](https://github.com/RubyLichtenstein/RxTest/wiki/Custom-matchers)
  * [Create matcher from scratch](https://github.com/RubyLichtenstein/RxTest/wiki/Custom-matchers#1-create-matchers-from-scratch)
  * [Wrap existing matcher](https://github.com/RubyLichtenstein/RxTest/wiki/Custom-matchers#2-wrap-existing-matcher)
  * [Combine matchers with And/Or](https://github.com/RubyLichtenstein/RxTest/wiki/Custom-matchers#3-combine-matchers-with-andor)
  
## Download
- #### Gradle
```groovy
dependencies {       
    testImplementation 'com.rubylichtenstein:rxtest:1.0.5'
}
```

- #### Maven
```xml
<dependency>
    <groupId>com.rubylichtenstein</groupId>
    <artifactId>rxtest</artifactId>
    <version>1.0.5</version>
    <type>pom</type>
</dependency>
```
## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/RubyLichtenstein/RxTest/issues).

## Contribution 

Welcome :).



