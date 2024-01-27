package com.mikes.pedido.application.core.usecase.exception.order

import com.mikes.pedido.application.core.usecase.exception.AlreadyExistsException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OrderAlreadyExistsExceptionTest {
    @Test
    fun testExceptionMessage() {
        val exceptionMessage = "Order already exists"

        val alreadyExistsException = mockk<AlreadyExistsException>(relaxed = true)

        val orderAlreadyExistsException = OrderAlreadyExistsException(exceptionMessage)

        every { alreadyExistsException.message } returns exceptionMessage

        assertEquals(exceptionMessage, orderAlreadyExistsException.message)
    }
}
