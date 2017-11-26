package com.rubylichtenstein.rxkotlintest.core

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.observers.TestObserver


fun <T> Maybe<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T> = test().apply(action)
fun <T> Single<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T> = test().apply(action)
fun <T> Observable<T>.test(action: (TestObserver<T>) -> Unit): TestObserver<T> = test().apply(action)
fun Completable.test(action: (TestObserver<Void>) -> Unit): TestObserver<Void> = test().apply(action)
