package com.enclave.decker.recipeapp.roomdatabase.base

import io.reactivex.Completable

interface UseCase {

    interface Action<in TParam> : Single<TParam, Unit>

    interface ActionSuccessful<in TParam> {
        fun execute(param: TParam): Completable
    }

    interface Single<in TParam, TResult> {
        fun execute(param: TParam): io.reactivex.Single<Result<TResult>>
    }

    interface SingleSuccessful<in TParam, TResult> {
        fun execute(param: TParam): io.reactivex.Single<TResult>
    }

    interface Continuous<in TParam, TResult> {
        fun execute(param: TParam): io.reactivex.Observable<TResult>
    }
}
