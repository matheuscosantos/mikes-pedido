package com.mikes.pedido.adapter.inbound.message

import com.mikes.pedido.adapter.inbound.message.order.OrderProductionListener
import com.mikes.pedido.adapter.inbound.message.order.dto.OrderProductionMessage
import com.mikes.pedido.application.port.inbound.order.OrderProductionService
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderProductionListenerTest {

    private lateinit var orderProductionService: OrderProductionService
    private lateinit var orderProductionListener: OrderProductionListener

    @BeforeEach
    fun setUp() {
        orderProductionService = mockk()
        orderProductionListener = spyk(OrderProductionListener(orderProductionService))
    }

    @Test
    fun `test order production listener`() {
        val message = OrderProductionMessage(orderId = "456", status = "IN_PROGRESS")

        every { orderProductionService.process(any()) } returns Result.success(Unit)

        orderProductionListener.listener(message)

        verify { orderProductionListener.listener(message) }
        verify { orderProductionService.process(any()) }
    }
}
