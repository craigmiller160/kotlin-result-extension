package io.craigmiller160.kotlin.result

fun <T,R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
    if (isSuccess) {
        return transform(getOrThrow())
    }
    return this as Result<R>
}

fun <T> Result<T>.flatRecover(transform: (Throwable) -> Result<T>): Result<T> {
    if (isSuccess) {
        return this
    }
    return transform(exceptionOrNull()!!)
}

fun <T> Result<T>.flatRecoverCatching(transform: (Throwable) -> Result<T>): Result<T> {
    if (isSuccess) {
        return this
    }
    return runCatching {
        transform(exceptionOrNull()!!)
    }.flatten()
}

fun <T> Result<Result<T>>.flatten(): Result<T> {
    if (isSuccess) {
        return getOrThrow()
    }
    return Result.failure(exceptionOrNull()!!)
}