package com.rubylichtenstein.rxtest.extentions

import io.reactivex.*
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber

/**
 * @param T type of stream
 * @param action to apply on TestObserver<T>
 * @return TestObserver<T> so more tests can be done
 */
inline fun <T> Maybe<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T> = test().apply(action)

/**
 * @param T type of stream
 * @param action to apply on TestObserver<T>
 * @return TestObserver<T> so more tests can be done
 */
inline fun <T> Single<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T> = test().apply(action)

/**
 * @param T type of stream
 * @param action to apply on TestObserver<T>
 * @return TestObserver<T> so more tests can be done
 */
inline fun <T> Observable<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T> = test().apply(action)

/**
 * @param action to apply on TestObserver<Void>
 * @return TestObserver<Void> so more tests can be done
 */
inline fun Completable.test(action: (TestObserver<Void>) -> Unit): TestObserver<Void> = test().apply(action)

/**
 * @param T type of stream
 * @param action to apply on TestSubscriber<T>
 * @return TestSubscriber<T> so more tests can be done
 */
inline fun <T> Flowable<T>.test(action: (TestSubscriber<T>) -> Unit): TestSubscriber<T> = test().apply(action)


