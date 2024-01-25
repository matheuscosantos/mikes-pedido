package com.mikes.pedido.application.core.usecase.customer.exception

import com.mikes.pedido.application.core.usecase.exception.AlreadyExistsException
import com.mikes.pedido.application.core.usecase.exception.customer.CustomerAlreadyExistsException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CustomerAlreadyExistsExceptionTest {

    @Test
    fun testExceptionMessage() {
        val exceptionMessage = "Customer already exists"

        val alreadyExistsException = mockk<AlreadyExistsException>(relaxed = true)

        val customerAlreadyExistsException = CustomerAlreadyExistsException(exceptionMessage)

        every { alreadyExistsException.message } returns exceptionMessage

        assertEquals(exceptionMessage, customerAlreadyExistsException.message)
    }
}
