package com.mikes.pedido.application.core.usecase.exception.product

import com.mikes.pedido.application.core.usecase.exception.InvalidDomainStateException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InvalidProductStateExceptionTest {
    @Test
    fun testExceptionMessage() {
        val exceptionMessage = "Invalid product state"

        val invalidDomainStateException = mockk<InvalidDomainStateException>(relaxed = true)

        val invalidProductStateException = InvalidProductStateException(exceptionMessage)

        every { invalidDomainStateException.message } returns exceptionMessage

        assertEquals(exceptionMessage, invalidProductStateException.message)
    }
}
