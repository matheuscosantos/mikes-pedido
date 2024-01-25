package com.mikes.pedido.adapter.inbound.message
import com.mikes.pedido.adapter.inbound.message.order.OrderPaymentListener
import com.mikes.pedido.adapter.inbound.message.order.dto.OrderPaymentMessage
import com.mikes.pedido.application.port.inbound.order.OrderPaymentService
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderPaymentListenerTest {

    private lateinit var orderPaymentService: OrderPaymentService
    private lateinit var orderPaymentListener: OrderPaymentListener

    @BeforeEach
    fun setUp() {
        orderPaymentService = mockk()
        orderPaymentListener = spyk(OrderPaymentListener(orderPaymentService))
    }

    @Test
    fun `test order payment listener`() {
        val message = OrderPaymentMessage(orderId = "123", status = "SUCCESS")

        every { orderPaymentService.process(any()) } returns Result.success(Unit)

        orderPaymentListener.listener(message)

        verify { orderPaymentListener.listener(message) }
        verify { orderPaymentService.process(any()) }
    }
}
