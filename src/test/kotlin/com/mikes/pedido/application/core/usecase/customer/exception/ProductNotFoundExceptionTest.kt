package com.mikes.pedido.application.core.usecase.customer.exception

import com.mikes.pedido.application.core.usecase.exception.NotFoundException
import com.mikes.pedido.application.core.usecase.exception.product.ProductNotFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductNotFoundExceptionTest {

    @Test
    fun testExceptionMessage() {
        val exceptionMessage = "Product not found"

        val notFoundException = mockk<NotFoundException>(relaxed = true)

        val productNotFoundException = ProductNotFoundException(exceptionMessage)

        every { notFoundException.message } returns exceptionMessage

        assertEquals(exceptionMessage, productNotFoundException.message)
    }
}
