package com.mikes.pedido.application.core.domain.exception.customer

import com.mikes.pedido.application.core.domain.exception.InvalidValueException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InvalidCpfExceptionTest {
    @Test
    fun testExceptionMessage() {
        val exceptionMessage = "Invalid CPF"

        val invalidValueException = mockk<InvalidValueException>(relaxed = true)

        val invalidCpfException = InvalidCpfException(exceptionMessage)

        every { invalidValueException.message } returns exceptionMessage

        assertEquals(exceptionMessage, invalidCpfException.message)
    }
}
