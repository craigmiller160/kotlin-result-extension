package io.craigmiller160.kotlin.result

/**
 * 1) Returns the Result of the given [transform] function applied to the encapsulated value
 * if this instance represents [success][Result.isSuccess] and [predicate] applied to the
 * encapsulated value returns `true`.
 * 2) Returns the original encapsulated value if this instance represents [success][Result.isSuccess]
 * and [predicate] applied to the encapsulated value returns `false`.
 * 3) Returns the original encapsulated [Throwable] exception if it is [failure][Result.isFailure].
 *
 * Note, that this function rethrows any [Throwable] exception thrown by [transform] or [predicate]
 * functions.
 * See [flatMapCatching] for an alternative that encapsulates exceptions.
 */
inline fun <R, T : R> Result<T>.flatMapIf(
    predicate: (value: T) -> Boolean,
    transform: (value: T) -> Result<R>
): Result<R> = flatMap {
    if (predicate(it)) transform(it)
    else Result.success(it)
}

/**
 * 1) Returns the Result of the given [transform] function applied to the encapsulated value
 * if this instance represents [success][Result.isSuccess] and [predicate] applied to the
 * encapsulated value returns `true`.
 * 2) Returns the original encapsulated value if this instance represents [success][Result.isSuccess]
 * and [predicate] applied to the encapsulated value returns `false`.
 * 3) Returns the original encapsulated [Throwable] exception if it is [failure][Result.isFailure].
 *
 * This function catches any [Throwable] exception thrown by [transform] and [predicate] functions
 * and encapsulates it as a failure.
 * See [flatMap] for an alternative that rethrows exceptions from `transform` and `predicate` function.
 */
inline fun <R, T : R> Result<T>.flatMapCatchingIf(
    predicate: (value: T) -> Boolean,
    transform: (value: T) -> Result<R>
): Result<R> = flatMapCatching {
    if (predicate(it)) transform(it)
    else Result.success(it)
}

/**
 * 1) Returns the Result of the given [transform] function applied to the encapsulated [Throwable]
 * exception if this instance represents [failure][Result.isFailure] and [predicate] applied to
 * the encapsulated [Throwable] exception returns `true`.
 * 2) Returns the original encapsulated [Throwable] exception if this instance represents
 * [failure][Result.isFailure] and [predicate] applied to the encapsulated [Throwable] exception
 * returns `false`.
 * 3) Returns the original encapsulated value if it is [success][Result.isSuccess].
 *
 * Note, that this function rethrows any [Throwable] exception thrown by [transform] and [predicate]
 * functions.
 * See [flatRecoverCatching] for an alternative that encapsulates exceptions.
 */
inline fun <R, T : R> Result<T>.flatRecoverIf(
    predicate: (Throwable) -> Boolean,
    transform: (Throwable) -> Result<R>
): Result<R> = flatRecover {
    if (predicate(it)) transform(it)
    else Result.failure(it)
}

/**
 * 1) Returns the Result of the given [transform] function applied to the encapsulated [Throwable]
 * exception if this instance represents [failure][Result.isFailure] and [predicate] applied to
 * the encapsulated [Throwable] exception returns `true`.
 * 2) Returns the original encapsulated [Throwable] exception if this instance represents
 * [failure][Result.isFailure] and [predicate] applied to the encapsulated [Throwable] exception
 * returns `false`.
 * 3) Returns the original encapsulated value if it is [success][Result.isSuccess].
 *
 * This function catches any [Throwable] exception thrown by [transform] and [predicate] functions
 * and encapsulates it as a failure.
 * See [flatRecover] for an alternative that rethrows exceptions.
 */
inline fun <R, T : R> Result<T>.flatRecoverCatchingIf(
    predicate: (Throwable) -> Boolean,
    transform: (Throwable) -> Result<R>
): Result<R> = flatRecoverCatching {
    if (predicate(it)) transform(it)
    else Result.failure(it)
}