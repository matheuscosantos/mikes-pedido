package com.mikes.pedido.application.core.usecase.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.mapper.order.OrderDomainMapper
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.Result.Companion.success

internal class FindOrderUseCaseTest {
    @Test
    fun `when finding orders with description, expect that`() {
        val expectedOrder = mockk<Order>()

        val orderRepository = mockOrderRepository()
        val orderDomainMapper = mockOrderDomainMapper(expectedOrder)

        val actualOrder =
            FindOrderUseCase(orderRepository, orderDomainMapper)
                .findOrdersWithDescriptions()
                .getOrThrow()
                .first()

        Assertions.assertEquals(expectedOrder, actualOrder)
    }

    @Test
    fun `when finding orders, expect that`() {
        val expectedOrder = mockk<Order>()
        val orderId = OrderId.generate()

        val orderRepository = mockOrderRepository()
        val orderDomainMapper = mockOrderDomainMapper(expectedOrder)

        val actualOrder =
            FindOrderUseCase(orderRepository, orderDomainMapper)
                .find(orderId)
                .getOrThrow()

        Assertions.assertEquals(expectedOrder, actualOrder)
    }

    private fun mockOrderRepository() =
        mockk<OrderRepository>().also {
            every { it.findById(any()) } returns mockk()
            every { it.findOrdersWithDescriptions() } returns listOf(mockk())
        }

    private fun mockOrderDomainMapper(order: Order) =
        mockk<OrderDomainMapper>().also {
            every { it.new(any<OrderOutboundResponse>()) } returns success(order)
        }
}
