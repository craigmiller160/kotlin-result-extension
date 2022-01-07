# Kotlin Result Extension

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

- [`flatMap`](#flatMap)
- [`flatMapCatching`](#flatMapCatching)
- [`flatRecover`](#flatRecover)
- [`flatReceoverCatching`](#flatReceoverCatching)
- [`flatten`](#flatten)

#### `flatMap`

Transforms the value of a successful `Result` and expects the result of the transformation to return
a `Result`. Using just `map`, this leads to `Result<Result<T>>`, but `flatMap` will unwrap the
outer `Result` so only the inner one is returned.

```kotlin
val doMoreReturnResult = { value -> "More: $value" }

val result = runCatching { "Start Value" }
    .flatMap { startValue -> doMoreReturnResult(startValue) }
    .getOrThrow()

// `result` is `"More: Start Value"`
```

#### `flatMapCatching`

Same as `flatMap` but catches exceptions that could be thrown by the transforming function.

```kotlin
val result = runCatching { "Start Value" }
    .flatMap { startValue -> throw RuntimeException("MyException") }
    .getOrThrow()

// `result` is `Result.failure(RuntimeException("MyException"))`
```

#### `flatRecover`

Similar to the existing `recover` function, only it expects a `Result` to be returned from the
transformation. Like the others, it will unwrap the `Result` to propagate it. Useful for recovering
with operations that return a result.

```kotlin
val result = runCatching { throw RuntimeException("Dying") }
    .flatRecover { Result.success("Hello World") }
    .getOrThrow()
// `result` is `Hello World`
```

#### `flatRecoverCatching`

Same as `flatRecover`, but it can handle exceptions being thrown within the function body.

```kotlin
val result = runCatching { throw RuntimeException("Dying") }
    .flatRecoverCatching { throw RuntimeException("MyException") }
    .getOrThrow()
// `result` is `Result.failure(RuntimeException("MyException"))`
```

#### `flatten`

A simple way to unwrap nested Results from chained operations.

```kotlin
val doMoreReturnResult = { value -> "More: $value" }

val result = runCatching { "Start Value" }
    .map { startValue -> doMoreReturnResult(startValue) }
    .flatten()
    .getOrThrow()

// "result" is "More: Start Value"
```

# Authors

### Creator

[Craig Millers](https://github.com/craigmiller160)

### Contributors

[Enrico Siboni](https://github.com/siboxd/)