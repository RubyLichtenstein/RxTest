package com.rubylichtenstein.rxkotlintest

import com.rubylichtenstein.rxkotlintest.matchers.complete
import com.rubylichtenstein.rxkotlintest.core.shouldEmit
import io.kotlintest.matchers.should
import io.kotlintest.specs.BehaviorSpec
import io.reactivex.subjects.PublishSubject

class Readme : BehaviorSpec(){

    init {
        //Given value, subject
        val value = "HelloRxKotlinTest"
        val subject = PublishSubject.create<String>()
        val subjectTest = subject.test()

        //When subject emits value
        subject.onNext(value)

        //Then value has emitted
        subjectTest.assertValue(value)

        //When call subject onComplete
        subject.onComplete()

        //Then subject complete with no errors
        subjectTest.assertComplete()
        subjectTest.assertNoErrors()

        Given("Value, subject") {
            val value = "Hello Rx Kotlin Test"
            val subject = PublishSubject.create<String>()
            val subjectTest = subject.test()

            When("subject emit value") {
                subject.onNext(value)

                Then("value emitted") {
                    subjectTest shouldEmit value
                }
            }

            When("call subject onComplete") {
                subject.onComplete()

                Then("subject complete with no errors") {
                    subjectTest should complete()
                }
            }
        }
    }


}
