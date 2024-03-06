package com.mikes.pedido.application.core.usecase.order

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderPrice
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.mapper.order.OrderDomainMapper
import com.mikes.pedido.application.port.inbound.customer.FindCustomerService
import com.mikes.pedido.application.port.inbound.order.dto.CreateOrderInboundRequest
import com.mikes.pedido.application.port.inbound.order.dto.CreateOrderItemInboundRequest
import com.mikes.pedido.application.port.inbound.product.FindProductService
import com.mikes.pedido.application.port.outbound.order.OrderReceivedMessenger
import com.mikes.pedido.application.port.outbound.order.OrderRepository
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.Result.Companion.success

internal class CreateOrderUseCaseTest {
    @Test
    fun `when creating order, expect to save and notify it`() {
        val customerId = CustomerId.generate()
        val productId = ProductId.generate().value
        val quantity = 1L

        val expectedOrder = mockOrder()

        val orderRepository = mockOrderRepository()
        val orderDomainMapper = mockOrderDomainMapper(expectedOrder)
        val findCustomerService = mockFindCustomerService(customerId)
        val findProductService = mockFindProductService()
        val orderReceivedMessenger = mockOrderReceivedMessenger()

        val createOrderUseCase =
            CreateOrderUseCase(
                orderRepository,
                orderDomainMapper,
                findCustomerService,
                findProductService,
                orderReceivedMessenger,
            )

        val request =
            CreateOrderInboundRequest(customerId.value, listOf(CreateOrderItemInboundRequest(productId, quantity)))

        createOrderUseCase.create(request)
            .getOrThrow()

        verify { orderRepository.save(any()) }
        verify { orderReceivedMessenger.send(any()) }
    }

    private fun mockOrderRepository() =
        mockk<OrderRepository>().also {
            every { it.findNumber() } returns 1
            every { it.save(any()) } returns mockk()
        }

    private fun mockOrderDomainMapper(order: Order) =
        mockk<OrderDomainMapper>().also {
            every { it.new(any(), any(), any(), any(), any(), any(), any()) } returns success(order)
            every { it.new(any<OrderOutboundResponse>()) } returns success(order)
            every { it.new(any(), any(), any()) } returns success(mockk())
        }

    private fun mockFindCustomerService(customerId: CustomerId) =
        mockk<FindCustomerService>().also {
            every { it.find(any()) } returns success(mockCustomer(customerId))
        }

    private fun mockFindProductService() =
        mockk<FindProductService>().also {
            every { it.find(any(), any()) } returns success(mockk())
        }

    private fun mockOrderReceivedMessenger() =
        mockk<OrderReceivedMessenger>().also {
            every { it.send(any()) } returns Unit
        }

    private fun mockCustomer(customerId: CustomerId) =
        mockk<Customer>().also {
            every { it.id } returns customerId
        }

    private fun mockOrder() =
        mockk<Order>().also {
            every { it.id } returns OrderId.generate()
            every { it.price } returns OrderPrice.new(BigDecimal.ONE).getOrThrow()
        }
}
