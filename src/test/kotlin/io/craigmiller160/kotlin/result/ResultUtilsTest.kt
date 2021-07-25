package io.craigmiller160.kotlin.result

import org.junit.jupiter.api.Test
import java.io.IOException
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ResultUtilsTest {

    @Test
    fun `flatMap a successful transform onto a successful Result`() {
        val result = runCatching {
            "Hello World"
        }
                .flatMap { Result.success(123) }
                .getOrThrow()
        assertEquals(123, result)
    }

    @Test
    fun `flatMap a failed transform onto a successful Result`() {
        assertFailsWith<IOException> {
            runCatching {
                "Hello World"
            }
                    .flatMap { Result.failure<Int>(IOException("IO Dying")) }
                    .getOrThrow()
        }
    }

    @Test
    fun `flatMap a successful transform onto a failed Result`() {
        assertFailsWith<RuntimeException> {
            runCatching {
                throw RuntimeException("Dying")
            }
                    .flatMap { Result.success(123) }
                    .getOrThrow()
        }
    }

    @Test
    fun `flatMap a failed transform onto a failed Result`() {
        assertFailsWith<RuntimeException> {
            runCatching {
                throw RuntimeException("Dying")
            }
                    .flatMap { Result.failure<Int>(IOException("IO Dying")) }
                    .getOrThrow()
        }
    }

    @Test
    fun `flatten a Result with both levels successful`() {
        val result = Result.success(Result.success("Hello World"))
                .flatten()
                .getOrThrow()
        assertEquals("Hello World", result)
    }

    @Test
    fun `flatten a Result with a successful outer level and failed inner level`() {
        assertFailsWith<IOException> {
            Result.success(Result.failure<String>(IOException("IO Dying")))
                    .flatten()
                    .getOrThrow()
        }
    }

    @Test
    fun `recoverAndFlatten with success result`() {
        val result = runCatching { throw RuntimeException("Dying") }
                .recoverAndFlatten { Result.success("Hello World") }
                .getOrThrow()
        assertEquals("Hello World", result)
    }

    @Test
    fun `recoverAndFlatten with failed result`() {
        assertFailsWith<IOException> {
            runCatching { throw RuntimeException("Dying") }
                    .recoverAndFlatten { Result.failure<Int>(IOException("IO Dying")) }
                    .getOrThrow()
        }
    }

    @Test
    fun `recoverAndFlatten where Result is already success`() {
        val result = Result.success("Success")
                .recoverAndFlatten { Result.success("Hello World") }
                .getOrThrow()
        assertEquals("Success", result)
    }

    @Test
    fun `recoverCatchingAndFlatten with success result`() {
        val result = runCatching { throw RuntimeException("Dying") }
                .recoverCatchingAndFlatten { Result.success("Hello World") }
                .getOrThrow()
        assertEquals("Hello World", result)
    }

    @Test
    fun `recoverCatchingAndFlatten with failed result`() {
        assertFailsWith<IOException> {
            runCatching { throw RuntimeException("Dying") }
                    .recoverCatchingAndFlatten { Result.failure<Int>(IOException("IO Dying")) }
                    .getOrThrow()
        }
    }

    @Test
    fun `recoverCatchingAndFlatten that catches exception`() {
        assertFailsWith<IOException> {
            runCatching { throw RuntimeException("Dying") }
                    .recoverCatchingAndFlatten { throw IOException("IO Dying") }
                    .getOrThrow()
        }
    }

    @Test
    fun `recoverCatchingAndFlatten where Result is already success`() {
        val result = Result.success("Success")
                .recoverCatchingAndFlatten { Result.success("Hello World") }
                .getOrThrow()
        assertEquals("Success", result)
    }

}