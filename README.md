# Kotlin Result Extension

[![](https://jitpack.io/v/siboxd/kotlin-result-extension.svg)](https://jitpack.io/#siboxd/kotlin-result-extension)

This project adds several new methods to the Kotlin `Result` (`kotlin.Result<T>`) type to facilitate
more robust, functional-style error handling. The main focus is on chaining `Result` operations
together and handling more complex and nested structures.

These extensions unleash all the "monadic power" of `kotlin.Result`, making it a
real [Monad](https://en.wikipedia.org/wiki/Monad_(functional_programming)).

## Usage

1. Add it in your root `build.gradle` at the end of repositories:

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency

```groovy
dependencies {
    implementation("com.github.siboxd:kotlin-result-extension:${latestVersion}")
}
```

## Functionalities

### Overview

- *[Flattening extensions](#Flattening extensions)*:

    - [`flatMap`](#flatMap)
    - [`flatMapCatching`](#flatMapCatching)
    - [`flatRecover`](#flatRecover)
    - [`flatReceoverCatching`](#flatReceoverCatching)
    - [`flatten`](#flatten)

- *[Conditional extensions](#Conditional extensions)*:

    - [`mapIf`](#mapIf)
    - [`mapCatchingIf`](#mapCatchingIf)
    - [`recoverIf`](#recoverIf)
    - [`recoverCatchingIf`](#recoverCatchingIf)

- *[Conditional flattening extensions](#Conditional flattening extensions)*:

    - [`flatMapIf`](#flatMapIf)
    - [`flatMapCatchingIf`](#flatMapCatchingIf)
    - [`flatRecoverIf`](#flatRecoverIf)
    - [`flatRecoverCatchingIf`](#flatRecoverCatchingIf)

#### Flattening extensions

These extensions make possible the chaining of operations avoiding `Result<Result<T>>` compositions.

##### `flatMap`

Transforms the value of a successful `Result` and expects the result of the transformation to return
a `Result`. Using just `map`, this leads to `Result<Result<T>>`, but `flatMap` will unwrap the
outer `Result` so only the inner one is returned.

```kotlin
val doMoreReturnResult = { value: String ->
    Result.success("More: $value")
}

val result = runCatching { "Start Value" }
    .flatMap { startValue -> doMoreReturnResult(startValue) }
    .getOrThrow()

// `result` is `"More: Start Value"`
```

##### `flatMapCatching`

Same as `flatMap` but catches exceptions that could be thrown by the transforming function.

```kotlin
val result = runCatching { "Start Value" }
    .flatMapCatching<String, String> { startValue ->
        throw RuntimeException("MyException")
    }

// `result` is `Result.failure(RuntimeException("MyException"))`
```

##### `flatRecover`

Similar to the existing `recover` function, only it expects a `Result` to be returned from the
transformation. Like the others, it will unwrap the `Result` to propagate it. Useful for recovering
with operations that return a result.

```kotlin
val result = runCatching { throw RuntimeException("Dying") }
    .flatRecover { Result.success("Hello World") }
    .getOrThrow()

// `result` is `Hello World`
```

##### `flatRecoverCatching`

Same as `flatRecover`, but it can handle exceptions being thrown within the function body.

```kotlin
val result = runCatching { throw RuntimeException("Dying") }
    .flatRecoverCatching { throw RuntimeException("MyException") }

// `result` is `Result.failure(RuntimeException("MyException"))`
```

##### `flatten`

A simple way to unwrap nested Results from chained operations.

```kotlin
val doMoreReturnResult = { value: String ->
    Result.success("More: $value")
}

val result = runCatching { "Start Value" }
    .map { startValue -> doMoreReturnResult(startValue) }
    .flatten()
    .getOrThrow()

// "result" is "More: Start Value"
```

#### Conditional extensions

These extensions make possible to conditionally apply a transformation *only if* a
certain `predicate` is satisfied.

##### `mapIf`

If the provided predicate returns `true`, transforms the value of a successful `Result` into another
value.

```kotlin
val doMoreReturnResult = { value: String -> "More: $value" }

val result = runCatching { "Start Value" }
    .mapIf({ it.startsWith("Start") }) { startValue ->
        doMoreReturnResult(startValue)
    }
    .getOrThrow()

// `result` is `"More: Start Value"`
```

##### `mapCatchingIf`

Same as `mapIf` but catches exceptions that could be thrown by the `predicate` or `transform`
functions.

```kotlin
val result = runCatching { "Start Value" }
    .mapCatchingIf({ it.startsWith("S") }) { startValue ->
        throw RuntimeException("MyException")
    }

// `result` is `Result.failure(RuntimeException("MyException"))`
```

##### `recoverIf`

If the provided predicate returns `true`, recovers the exception of a failed `Result` into another
value.

```kotlin
val result = runCatching { throw RuntimeException("Dying") }
    .recoverIf({ it is RuntimeException }) { "Hello World" }
    .getOrThrow()

// `result` is `Hello World`
```

##### `recoverCatchingIf`

Same as `recoverIf`, but it can handle exceptions being thrown within the `predicate`
and `transform` functions' body.

```kotlin
val result = runCatching { throw RuntimeException("Dying") }
    .recoverCatchingIf({ it is RuntimeException }) {
        throw RuntimeException("MyException")
    }

// `result` is `Result.failure(RuntimeException("MyException"))`
```

#### Conditional flattening extensions

These extensions combine the utility of *flattening* extensions with that of *conditional* ones.

##### `flatMapIf`

Combines functionalities of `mapIf` and `flatMap` explained above.

```kotlin
val doMoreReturnResult = { value: String ->
    Result.success("More: $value")
}

val result = runCatching { "Start Value" }
    .flatMapIf({ it.startsWith("S") }) { startValue ->
        doMoreReturnResult(startValue)
    }
    .getOrThrow()

// `result` is `"More: Start Value"`
```

##### `flatMapCatchingIf`

Combines functionalities of `mapCatchingIf` and `flatMapCatching` explained above.

```kotlin
val result = runCatching { "Start Value" }
    .flatMapCatchingIf({ it.startsWith("S") }) { startValue ->
        throw RuntimeException("MyException")
    }

// `result` is `Result.failure(RuntimeException("MyException"))`
```

##### `flatRecoverIf`

Combines functionalities of `recoverIf` and `flatRecover` explained above.

```kotlin
val result = runCatching { throw RuntimeException("Dying") }
    .flatRecoverIf({ it is RuntimeException }) { Result.success("Hello World") }
    .getOrThrow()

// `result` is `Hello World`
```

##### `flatRecoverCatchingIf`

Combines functionalities of `recoverCatchingIf` and `flatRecoverCatching` explained above.

```kotlin
val result = runCatching { throw RuntimeException("Dying") }
    .flatRecoverCatchingIf({ it is RuntimeException }) { throw RuntimeException("MyException") }

// `result` is `Result.failure(RuntimeException("MyException"))`
```

# Authors

### Creator

[Craig Millers](https://github.com/craigmiller160)

### Contributors

[Enrico Siboni](https://github.com/siboxd/)
