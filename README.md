# Kotlin Result Extension

This project adds several new methods to the Kotlin `Result` type to facilitate more robust, functional-style error handling. The main focus is on chaining `Result` operations together and handling more complex and nested structures.

## New Functions

### flatMap

Transforms the value of a successful `Result` and expects the result of the transformation to return a `Result`. Using just `map`, this leads to `Result<Result<T>>`, but `flatMap` will unwrap the outer `Result` so only the inner one is returned.

```
val doMoreReturnResult = { value -> "More: $value" }

val result = runCatching {
	"Start Value"
}
	.flatMap { startValue -> doMoreReturnResult(startValue) }
	.getOrThrow()

// "result" is "More: Start Value"
```

### flatten

A simple way to unwrap nested Results from chained operations.

```
val doMoreReturnResult = { value -> "More: $value" }

val result = runCatching {
	"Start Value"
}
	.map { startValue -> doMoreReturnResult(startValue) }
	.flatten()
	.getOrThrow()

// "result" is "More: Start Value"
```
### flatRecover

Similar to the existing `recover` function, only it expects a `Result` to be returned from the transformation. Like the others, it will unwrap the `Result` to propagate it.

```

```

### recoverCatchingAndFlatten