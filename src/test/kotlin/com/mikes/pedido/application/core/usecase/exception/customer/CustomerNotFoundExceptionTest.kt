package com.mikes.pedido.application.core.usecase.exception.customer

import com.mikes.pedido.application.core.usecase.exception.NotFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CustomerNotFoundExceptionTest {
    @Test
    fun testExceptionMessage() {
        val exceptionMessage = "Customer not found"

        val notFoundException = mockk<NotFoundException>(relaxed = true)

        val customerNotFoundException = CustomerNotFoundException(exceptionMessage)

        every { notFoundException.message } returns exceptionMessage

        assertEquals(exceptionMessage, customerNotFoundException.message)
    }
}
