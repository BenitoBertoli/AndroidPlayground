package com.benitobertoli.androidplayground.core

sealed class AppResult<out T, out E> {
    data class Success<out T>(val value: T) : AppResult<T, Nothing>()
    data class Failure<out E>(val error: E) : AppResult<Nothing, E>()

    inline fun <C> fold(success: (T) -> C, failure: (E) -> C): C = when (this) {
        is Success -> success(value)
        is Failure -> failure(error)
    }

    inline fun <K> map(mapper: (T) -> K): AppResult<K, E> {
        return fold({ Success(mapper(it)) }, { Failure(it) })
    }
}

typealias SimpleResult<T> = AppResult<T, Throwable>