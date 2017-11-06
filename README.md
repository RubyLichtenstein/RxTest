# RxKotlinTest
Easy rx testing with kotlin assertionWrapper
[![Build Status](https://travis-ci.org/RubyLichtenstein/RxKotlinTest.svg?branch=master)](https://travis-ci.org/RubyLichtenstein/RxKotlinTest)
[![Coverage Status](https://coveralls.io/repos/github/RubyLichtenstein/RxKotlinTest/badge.svg?branch=master)](https://coveralls.io/github/RubyLichtenstein/RxKotlinTest?branch=master)


## Usage

```kotlin
Observable.just("Hello")
                .assertionWrapper {
                    it should complete()
                    it shouldHave noErrors()
                    it shouldHave value("Hello")
                }
                
Observable.never<Unit>()
                .assertionWrapper {
                    it should notComplete()
                    it shouldHave noErrors()
                    it shouldHave valueCount(0)
                }
```

## Binaries
Gradle:

## Contributing
