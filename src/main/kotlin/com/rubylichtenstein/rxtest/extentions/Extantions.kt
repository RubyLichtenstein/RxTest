package com.rubylichtenstein.rxtest.extentions

import io.reactivex.*
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber

inline fun <T> Maybe<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T> = test().apply(action)
inline fun <T> Single<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T> = test().apply(action)
inline fun <T> Observable<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T> = test().apply(action)
inline fun Completable.test(action: (TestObserver<Void>) -> Unit): TestObserver<Void> = test().apply(action)
inline fun <T> Flowable<T>.test(action: (TestSubscriber<T>) -> Unit): TestSubscriber<T> = test().apply(action)

