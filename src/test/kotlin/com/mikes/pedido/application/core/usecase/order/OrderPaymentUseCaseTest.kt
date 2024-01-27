package com.mikes.pedido.application.core.usecase.order

import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderPaymentStatus
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import com.mikes.pedido.application.port.inbound.order.dto.OrderPaymentInboundRequest
import com.mikes.pedido.application.port.outbound.order.OrderConfirmedMessenger
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.Result.Companion.success

internal class OrderPaymentUseCaseTest {
    @Test
    fun `when receive WAITING message, just wait`() {
        val orderId = OrderId.generate()
        val order = mockOrder(orderId, OrderStatus.RECEIVED)
        val paymentStatus = OrderPaymentStatus.WAITING

        val orderConfirmedMessenger = mockOrderConfirmedMessenger()
        val findOrderService = mockFindOrderService(order)
        val orderRepository = mockOrderRepository()

        val orderPaymentUseCase = OrderPaymentUseCase(orderConfirmedMessenger, findOrderService, orderRepository)

        val request =
            OrderPaymentInboundRequest(
                orderId.value,
                paymentStatus.name,
            )

        orderPaymentUseCase.process(request)

        verify(exactly = 0) { order.updateStatus(any()) }
        verify(exactly = 0) { orderRepository.save(any()) }
        verify(exactly = 0) { orderConfirmedMessenger.send(any()) }
    }

    @Test
    fun `when receive REFUSED message, cancel order`() {
        val orderId = OrderId.generate()
        val order = mockOrder(orderId, OrderStatus.RECEIVED)
        val paymentStatus = OrderPaymentStatus.REFUSED

        val orderConfirmedMessenger = mockOrderConfirmedMessenger()
        val findOrderService = mockFindOrderService(order)
        val orderRepository = mockOrderRepository()

        val orderPaymentUseCase = OrderPaymentUseCase(orderConfirmedMessenger, findOrderService, orderRepository)

        val request =
            OrderPaymentInboundRequest(
                orderId.value,
                paymentStatus.name,
            )

        orderPaymentUseCase.process(request)

        verify(exactly = 1) { order.updateStatus(eq(OrderStatus.CANCELLED)) }
        verify(exactly = 1) { orderRepository.save(any()) }
        verify(exactly = 0) { orderConfirmedMessenger.send(any()) }
    }

    @Test
    fun `when receive ACCEPTED message, confirm order`() {
        val orderId = OrderId.generate()
        val order = mockOrder(orderId, OrderStatus.RECEIVED)
        val paymentStatus = OrderPaymentStatus.ACCEPTED

        val orderConfirmedMessenger = mockOrderConfirmedMessenger()
        val findOrderService = mockFindOrderService(order)
        val orderRepository = mockOrderRepository()

        val orderPaymentUseCase = OrderPaymentUseCase(orderConfirmedMessenger, findOrderService, orderRepository)

        val request =
            OrderPaymentInboundRequest(
                orderId.value,
                paymentStatus.name,
            )

        orderPaymentUseCase.process(request)

        verify(exactly = 1) { order.updateStatus(eq(OrderStatus.PAID)) }
        verify(exactly = 1) { orderRepository.save(any()) }
        verify(exactly = 1) { orderConfirmedMessenger.send(any()) }
    }

    private fun mockOrderConfirmedMessenger() =
        mockk<OrderConfirmedMessenger>().also {
            every { it.send(any()) } returns Unit
        }

    private fun mockFindOrderService(order: Order) =
        mockk<FindOrderService>().also {
            every { it.find(any()) } returns success(order)
        }

    private fun mockOrderRepository() =
        mockk<OrderRepository>().also {
            every { it.save(any()) } returns mockk()
        }

    private fun mockOrder(
        orderId: OrderId,
        orderStatus: OrderStatus,
    ): Order =
        mockk<Order>().also {
            every { it.id } returns orderId
            every { it.orderStatus } returns orderStatus

            every { it.updateStatus(any()) } answers {
                val newOrderStatus = it.invocation.args[0] as OrderStatus
                mockOrder(orderId, newOrderStatus)
            }
        }
}
