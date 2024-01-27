package com.mikes.pedido.application.core.usecase.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import com.mikes.pedido.application.port.inbound.order.dto.OrderProductionInboundRequest
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.Result.Companion.success

internal class OrderProductionUseCaseTest {
    @Test
    fun `when receiving event from order production, expect update`() {
        val orderId = OrderId.generate()
        val orderStatus = OrderStatus.PAID
        val productionStatus = OrderStatus.PREPARING

        val order = mockOrder(orderStatus)

        val findOrderService = mockFindOrderService(order)
        val orderRepository = mockOrderRepository()

        val orderProductionUseCase = OrderProductionUseCase(findOrderService, orderRepository)

        val request =
            OrderProductionInboundRequest(
                orderId.value,
                productionStatus.name,
            )

        orderProductionUseCase.process(
            request,
        )

        verify { order.updateStatus(eq(productionStatus)) }
        verify { orderRepository.save(any()) }
    }

    private fun mockFindOrderService(order: Order) =
        mockk<FindOrderService>().also {
            every { it.find(any()) } returns success(order)
        }

    private fun mockOrderRepository() =
        mockk<OrderRepository>().also {
            every { it.save(any()) } returns mockk()
        }

    private fun mockOrder(orderStatus: OrderStatus): Order =
        mockk<Order>().also {
            every { it.orderStatus } returns orderStatus
            every { it.updateStatus(any()) } answers {
                val newOrderStatus = it.invocation.args.first() as OrderStatus
                mockOrder(newOrderStatus)
            }
        }
}
