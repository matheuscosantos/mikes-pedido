package com.mikes.pedido.application.core.usecase.customer.exception

import com.mikes.pedido.application.core.domain.exception.InvalidValueException
import com.mikes.pedido.application.core.domain.exception.customer.InvalidPersonNameException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InvalidPersonNameExceptionTest {

    @Test
    fun testExceptionMessage() {
        val exceptionMessage = "Invalid person name"

        val invalidValueException = mockk<InvalidValueException>(relaxed = true)

        val invalidPersonNameException = InvalidPersonNameException(exceptionMessage)

        every { invalidValueException.message } returns exceptionMessage

        assertEquals(exceptionMessage, invalidPersonNameException.message)
    }
}
