package com.mikes.pedido.application.core.usecase.order

import com.mikes.pedido.application.core.domain.customer.Customer
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
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
        val cpf = "92979654078"
        val productId = ProductId.generate().value
        val quantity = 1L

        val expectedOrder = mockOrder()

        val orderRepository = mockOrderRepository()
        val orderDomainMapper = mockOrderDomainMapper(expectedOrder)
        val findCustomerService = mockFindCustomerService(cpf)
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
            CreateOrderInboundRequest(cpf, listOf(CreateOrderItemInboundRequest(productId, quantity)))

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

    private fun mockFindCustomerService(cpfValue: String) =
        mockk<FindCustomerService>().also {
            every { it.find(any(), any()) } returns success(mockCustomer(cpfValue))
        }

    private fun mockFindProductService() =
        mockk<FindProductService>().also {
            every { it.find(any(), any()) } returns success(mockk())
        }

    private fun mockOrderReceivedMessenger() =
        mockk<OrderReceivedMessenger>().also {
            every { it.send(any()) } returns Unit
        }

    private fun mockCustomer(cpfValue: String) =
        mockk<Customer>().also {
            every { it.cpf } returns Cpf.new(cpfValue).getOrThrow()
        }

    private fun mockOrder() =
        mockk<Order>().also {
            every { it.id } returns OrderId.generate()
            every { it.price } returns OrderPrice.new(BigDecimal.ONE).getOrThrow()
        }
}
