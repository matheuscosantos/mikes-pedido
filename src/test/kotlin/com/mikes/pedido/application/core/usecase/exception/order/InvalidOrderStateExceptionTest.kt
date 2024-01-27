package com.mikes.pedido.application.core.usecase.exception.order

import com.mikes.pedido.application.core.usecase.exception.InvalidDomainStateException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InvalidOrderStateExceptionTest {
    @Test
    fun testExceptionMessage() {
        val exceptionMessage = "Invalid order state"

        val invalidDomainStateException = mockk<InvalidDomainStateException>(relaxed = true)

        val invalidOrderStateException = InvalidOrderStateException(exceptionMessage)

        every { invalidDomainStateException.message } returns exceptionMessage

        assertEquals(exceptionMessage, invalidOrderStateException.message)
    }
}
