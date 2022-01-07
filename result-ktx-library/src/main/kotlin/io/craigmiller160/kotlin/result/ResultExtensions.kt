package io.craigmiller160.kotlin.result

/**
 * Returns the result of the given [transform] function applied to the encapsulated value
 * if this instance represents [success][Result.isSuccess] or the
 * original encapsulated [Throwable] exception if it is [failure][Result.isFailure].
 *
 * Note, that this function rethrows any [Throwable] exception thrown by [transform] function.
 * See [flatMapCatching] for an alternative that encapsulates exceptions.
 */
inline fun <R, T> Result<T>.flatMap(transform: (value: T) -> Result<R>): Result<R> = fold(
    onSuccess = { value -> transform(value) },
    onFailure = { error -> Result.failure(error) }
)

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