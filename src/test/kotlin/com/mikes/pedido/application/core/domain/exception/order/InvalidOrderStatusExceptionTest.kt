package com.mikes.pedido.application.core.domain.exception.order

import com.mikes.pedido.application.core.domain.exception.InvalidValueException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import util.AssertionsUtil

internal class InvalidOrderStatusExceptionTest {
    @Test
    fun `when creating InvalidOrderIdException, expect InvalidValueException type with message`() {
        val message = "message"
        val exception = InvalidOrderStatusException(message)

        Assertions.assertEquals(message, exception.message)
        AssertionsUtil.isInstanceType<InvalidValueException>(exception)
    }
}
