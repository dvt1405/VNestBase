package com.kt.basedata

sealed class DataState<T> {
    data class Success<T>(var value: T) : DataState<T>()
    data class Error<T>(var throwable: Throwable) : DataState<T>()
    data class ErrorWithData<T>(var value: T, var throwable: Throwable) : DataState<T>()
    data class Loaded<T>(var value: T) : DataState<T>()
    data class Loading<T>(var value: T?) : DataState<T>()
}