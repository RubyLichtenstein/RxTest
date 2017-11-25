package com.rubylichtenstein.rxkotlintest

import com.rubylichtenstein.rxkotlintest.matchers.complete
import com.rubylichtenstein.rxkotlintest.core.shouldEmit
import com.rubylichtenstein.rxkotlintest.core.test
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

class Readme {

    init {
        //Given value, subject
        val value = "HelloRxKotlinTest"
        val subject = ReplaySubject.create<String>()

        //When subject emits value
        subject.onNext(value)

        //Then value has emitted
        subject.test().assertValue(value)

        //When call subject onComplete
        subject.onComplete()

        //Then subject complete with no errors
        subject.test().assertComplete()
        subject.test().assertNoErrors()

//        Given("Value, subject") {
//            val value = "Hello Rx Kotlin Test"
//            val subject = ReplaySubject.create<String>()
//
//            When("subject emit value") {
//                subject.onNext(value)
//
//                Then("value emitted") {
//                    subject.test {
//                        it shouldEmit value
//                    }
//                }
//            }
//
//            When("call subject onComplete") {
//                subject.onComplete()
//
//                Then("subject complete with no errors") {
//                    subject.test {
////                        it should shouldcomplete()
//                    }
//                }
//            }
//        }
    }


}
