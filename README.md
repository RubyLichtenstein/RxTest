# RxTest &middot; [![Build Status](https://travis-ci.org/RubyLichtenstein/RxTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxTest) [![codecov](https://codecov.io/gh/RubyLichtenstein/RxTest/branch/master/graph/badge.svg)](https://codecov.io/gh/RubyLichtenstein/RxTest) [![Download](https://api.bintray.com/packages/rubylichtenstein/RxTest/com.rubylichtenstein.rxtest/images/download.svg)](https://bintray.com/rubylichtenstein/RxTest/com.rubylichtenstein.rxtest/_latestVersion) [![Kotlin version badge](https://img.shields.io/badge/kotlin-1.2.10-blue.svg)](http://kotlinlang.org/) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![Gitter chat](https://badges.gitter.im/gitterHQ/gitter.png)](https://gitter.im/RxTest/)



RxTest is a Kotlin library for testing RxJava.

* **Clear tests.**
* **Easy to extend.**
* **Simple to use.**

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

 - #### [Introduction](https://github.com/RubyLichtenstein/RxTest/wiki/Introducation)
 - #### [Examples](https://github.com/RubyLichtenstein/RxTest/wiki/Examples)
 - #### Components
 
   - [Matchers](https://github.com/RubyLichtenstein/RxTest/wiki/Matchers)
   - [Assertions](https://github.com/RubyLichtenstein/RxTest/wiki/Assertions)
   - [Extensions](https://github.com/RubyLichtenstein/RxTest/wiki/Extensions)    

 - #### [Create Matcher](https://github.com/RubyLichtenstein/RxTest/wiki/Create-matcher)
   - [From scratch](https://github.com/RubyLichtenstein/RxTest/wiki/Create-matcher#1-from-scratch)
   - [Wrap existing](https://github.com/RubyLichtenstein/RxTest/wiki/Create-matcher#2-wrap-existing)
   - [Combine with And/Or](https://github.com/RubyLichtenstein/RxTest/wiki/Create-matcher#3-combine-with-andor)

## Download
- #### Gradle
```groovy
dependencies {       
    ...
    testCompile 'com.rubylichtenstein:rxtest:1.0.4'
}
```

- #### Maven
```xml
<dependency>
    <groupId>com.rubylichtenstein</groupId>
    <artifactId>rxtest</artifactId>
    <version>1.0.4</version>
    <type>pom</type>
</dependency>
```
## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/RubyLichtenstein/RxTest/issues).

## Contribution 

Welcome :).


