package com.mikes.pedido.application.core.usecase.customer.exception

import com.mikes.pedido.application.core.usecase.exception.NotFoundException
import com.mikes.pedido.application.core.usecase.exception.order.OrderNotFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OrderNotFoundExceptionTest {

    @Test
    fun testExceptionMessage() {
        val exceptionMessage = "Order not found"

        val notFoundException = mockk<NotFoundException>(relaxed = true)

        val orderNotFoundException = OrderNotFoundException(exceptionMessage)

        every { notFoundException.message } returns exceptionMessage

        assertEquals(exceptionMessage, orderNotFoundException.message)
    }
}
