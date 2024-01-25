package com.mikes.pedido.application.core.usecase.customer.exception

import com.mikes.pedido.application.core.domain.exception.InvalidValueException
import com.mikes.pedido.application.core.domain.exception.customer.InvalidEmailException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InvalidEmailExceptionTest {

    @Test
    fun testExceptionMessage() {
        val exceptionMessage = "Invalid email"

        val invalidValueException = mockk<InvalidValueException>(relaxed = true)

        val invalidEmailException = InvalidEmailException(exceptionMessage)

        every { invalidValueException.message } returns exceptionMessage

        assertEquals(exceptionMessage, invalidEmailException.message)
    }
}
