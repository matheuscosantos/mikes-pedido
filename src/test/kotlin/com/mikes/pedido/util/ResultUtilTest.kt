package com.mikes.pedido.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

internal class ResultUtilTest {
    @Test
    fun `when flat mapping a success to another success, expect new success value`() {
        val expectedValue = 1

        val actualValue =
            success(0)
                .flatMap { success(expectedValue) }
                .getOrThrow()

        Assertions.assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `when flat mapping a success to failure, expect failure`() {
        assertThrows<Exception> {
            success(0)
                .flatMap { failure<Int>(Exception()) }
                .getOrThrow()
        }
    }

    @Test
    fun `when flat mapping a failure to success, expect failure`() {
        assertThrows<Exception> {
            failure<Int>(Exception())
                .flatMap { success(0) }
                .getOrThrow()
        }
    }

    @Test
    fun `when mapping failure for success, expect success`() {
        val expectedValue = 0

        val actualValue =
            success(expectedValue)
                .mapFailure { Exception() }
                .getOrThrow()

        Assertions.assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `when mapping failure for failure, expect new failure`() {
        val expectedException = Exception()

        val actualException =
            failure<Int>(Exception())
                .mapFailure { expectedException }
                .exceptionOrNull()!!

        Assertions.assertEquals(expectedException, actualException)
    }
}
