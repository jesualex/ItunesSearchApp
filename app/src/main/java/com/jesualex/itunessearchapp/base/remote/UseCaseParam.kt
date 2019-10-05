package com.jesualex.itunessearchapp.base.remote

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by jesualex on 21-03-19.
 */
abstract class UseCaseParam<T, U> {
    private val compositeDisposable = CompositeDisposable()
    protected val subscribeOn: Scheduler get() = Schedulers.io()
    protected val observeOn: Scheduler get() = AndroidSchedulers.mainThread()

    fun execute(param: U, disposableObserver: UseCaseObserver<T>) {
        createObservableUseCase(param)?.let {
            val observable = it.subscribeOn(subscribeOn).observeOn(observeOn)
            val observer = observable.subscribeWith(disposableObserver)

            compositeDisposable.add(observer)
        }
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    protected abstract fun createObservableUseCase(param: U): Observable<T>?
}
