package com.mikes.pedido.application.core.usecase.exception.customer
import com.mikes.pedido.application.core.usecase.exception.InvalidDomainStateException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InvalidCustomerStateExceptionTest {
    @Test
    fun testExceptionMessage() {
        val exceptionMessage = "Invalid customer state"

        val invalidDomainStateException = mockk<InvalidDomainStateException>(relaxed = true)

        val invalidCustomerStateException = InvalidCustomerStateException(exceptionMessage)

        every { invalidDomainStateException.message } returns exceptionMessage

        assertEquals(exceptionMessage, invalidCustomerStateException.message)
    }
}
