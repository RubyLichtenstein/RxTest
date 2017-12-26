package com.rubylichtenstein.rxtest.extentions

import io.reactivex.*
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber

fun <T> Maybe<T>.test(action: (TestObserver<T>) -> Unit) = test().apply(action)
fun <T> Single<T>.test(action: (TestObserver<T>) -> Unit) = test().apply(action)
fun <T> Observable<T>.test(action: (TestObserver<T>) -> Unit) = test().apply(action)
fun Completable.test(action: (TestObserver<Void>) -> Unit): TestObserver<Void> = test().apply(action)
fun <T> Flowable<T>.test(action: (TestSubscriber<T>) -> Unit) = test().apply(action)

