package com.kt.basedata

import io.reactivex.Observable
import retrofit2.HttpException

fun <T> Observable<T>.doRetry(refreshAccessToken: () -> Observable<T>): Observable<T> {
    return this.retryWhen {
        it.flatMap { throwable ->
            if (throwable is HttpException && throwable.code() == 401) {
                refreshAccessToken()
            } else {
                it
            }
        }
    }
}