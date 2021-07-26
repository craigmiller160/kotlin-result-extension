package io.craigmiller160.kotlin.result

fun <T,R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> =
        when {
            isSuccess -> transform(getOrThrow())
            else -> this as Result<R>
        }

fun <T> Result<T>.flatRecover(transform: (Throwable) -> Result<T>): Result<T> =
        when {
            isSuccess -> this
            else -> transform(exceptionOrNull()!!)
        }

fun <T> Result<T>.flatRecoverCatching(transform: (Throwable) -> Result<T>): Result<T> =
        when {
            isSuccess -> this
            else -> runCatching {
                transform(exceptionOrNull()!!)
            }.flatten()
        }

fun <T> Result<Result<T>>.flatten(): Result<T> =
        when {
            isSuccess -> getOrThrow()
            else -> Result.failure(exceptionOrNull()!!)
        }