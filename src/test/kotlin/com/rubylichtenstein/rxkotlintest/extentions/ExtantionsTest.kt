//package com.rubylichtenstein.rxkotlintest.extentions
//
//import com.rubylichtenstein.rxkotlintest.assertions.should
//import com.rubylichtenstein.rxkotlintest.matchers.complete
//import io.reactivex.Completable
//import io.reactivex.Maybe
//import io.reactivex.Observable
//import io.reactivex.Single
//import org.junit.Test
//
//
//internal class ExtensionsTest {
//
//    @Test
//    fun testTest() {
//        Observable.just("")
//                .test {
//                    it.assertComplete()
//                    it should complete()
//                }
//
//        Maybe.just("")
//                .test {
//                    it.assertComplete()
//                    it should complete()
//                }
//
//        Single.just("")
//                .test {
//                    it.assertComplete()
//                    it should complete()
//                }
//
//        Completable.complete()
//                .test {
//                    it.assertComplete()
//                    it should complete()
//                }
//    }
//}
