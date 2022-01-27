package io.craigmiller160.kotlin.result

import org.junit.Assert.assertEquals
import org.junit.Test

class ResultExtensionsConditionalTest {

    /** Exception used in tests */
    data class MyTestException(override val message: String? = null) : RuntimeException(message)

    /** Other exception used in tests */
    data class MyOtherTestException(override val message: String? = null) :
        RuntimeException(message)

    private val mySuccessResult = Result.success("Hello World")
    private val myOtherSuccessResult = Result.success(123)

    private val myFailureResult = Result.failure<String>(MyTestException("First"))
    private val myOtherFailureResult = Result.failure<String>(MyTestException("Second"))

    @Test
    fun flatMapIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatMapIf({ true }) { myOtherSuccessResult }

        assertEquals(myOtherSuccessResult, actualResult)
    }

    @Test
    fun flatMapIfOnSuccessfulResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatMapIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatMapIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatMapIf({ true }) { myFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatMapIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test(expected = MyTestException::class)
    fun flatMapIfOnSuccessfulResultIntoPredicateThrowingException() {
        mySuccessResult
            .flatMapIf({ throw MyTestException() }) { throw MyOtherTestException() }
    }

    @Test(expected = MyTestException::class)
    fun flatMapIfOnSuccessfulResultIntoTransformThrowingExceptionWithTrueCondition() {
        mySuccessResult
            .flatMapIf({ true }) { throw MyTestException() }
    }

    @Test
    fun flatMapIfOnSuccessfulResultIntoTransformThrowingExceptionWithFalseCondition() {
        mySuccessResult
            .flatMapIf({ false }) { throw MyTestException() }
    }

    @Test
    fun flatMapIfOnFailureResultIntoSuccessResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ true }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoSuccessResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ true }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoPredicateThrowingException() {
        val actualResult = myFailureResult
            .flatMapIf({ throw MyTestException() }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ true }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoTransformsThrowingExceptionWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ false }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatMapCatchingIf({ true }) { myOtherSuccessResult }

        assertEquals(myOtherSuccessResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatMapCatchingIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatMapCatchingIf({ true }) { myFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatMapCatchingIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoPredicateThrowingException() {
        val testException = MyTestException()

        val actualResult = mySuccessResult
            .flatMapCatchingIf({ throw testException }) { throw MyOtherTestException() }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoTransformThrowingExceptionWithTrueCondition() {
        val testException = MyTestException()

        val actualResult = mySuccessResult
            .flatMapCatchingIf({ true }) { throw testException }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoTransformThrowingExceptionWithFalseCondition() {
        val testException = MyTestException()

        val actualResult = mySuccessResult
            .flatMapCatchingIf({ false }) { throw testException }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoSuccessResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ true }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoSuccessResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ true }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoPredicateThrowingException() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ throw MyTestException() }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ true }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoTransformThrowingExceptionWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ false }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ true }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoSuccessfulResultWitFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ true }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoThrowingExceptionWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ true }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoThrowingExceptionWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ false }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnFailureResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatRecoverIf({ true }) { mySuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnFailureResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatRecoverIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatRecoverIf({ true }) { myOtherFailureResult }

        assertEquals(myOtherFailureResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatRecoverIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test(expected = MyTestException::class)
    fun flatRecoverIfOnFailureResultIntoPredicateThrowingException() {
        myFailureResult
            .flatRecoverIf({ throw MyTestException() }) { throw MyOtherTestException() }
    }

    @Test(expected = MyTestException::class)
    fun flatRecoverIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        myFailureResult
            .flatRecoverIf({ true }) { throw MyTestException() }
    }

    @Test
    fun flatRecoverIfOnFailureResultIntoTransformThrowingExceptionWithFalseCondition() {
        myFailureResult
            .flatRecoverIf({ false }) { throw MyTestException() }
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ true }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ true }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoThrowingExceptionWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ true }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoThrowingExceptionWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ false }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ true }) { mySuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ true }) { myOtherFailureResult }

        assertEquals(myOtherFailureResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoPredicateThrowingException() {
        val testException = MyTestException()

        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ throw testException }) { throw MyOtherTestException() }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        val testException = MyTestException()

        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ true }) { throw testException }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoTransformThrowingExceptionWithFalseCondition() {
        val testException = MyTestException()

        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ false }) { throw testException }

        assertEquals(myFailureResult, actualResult)
    }
}