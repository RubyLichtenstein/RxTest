# RxKotlinTest
## Testing RxJava2 made fun with kotlin DSL

[![Build Status](https://travis-ci.org/RubyLichtenstein/RxKotlinTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxKotlinTest)
[![codecov](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest/branch/master/graph/badge.svg)](https://codecov.io/gh/RubyLichtenstein/RxKotlinTest)



## Usage

```kotlin
Observable.just("Hello")
                .test {
                    it should complete()
                    it shouldHave noErrors()
                    it shouldHave value("Hello")
                }
                
Observable.never<Unit>()
                .test {
                    it should notComplete()
                    it shouldHave noErrors()
                    it shouldHave valueCount(0)
                }
```

## Binaries
Gradle:

## Contributing
