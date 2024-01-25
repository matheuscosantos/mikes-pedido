package com.mikes.pedido.adapter.inbound.controller.order

import com.mikes.pedido.adapter.inbound.controller.order.dto.CreateOrderRequest
import com.mikes.pedido.adapter.inbound.controller.order.dto.OrderDto
import com.mikes.pedido.adapter.inbound.controller.order.dto.OrderItemDto
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.OrderItem
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemQuantity
import com.mikes.pedido.application.core.domain.order.valueobject.OrderNumber
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.core.domain.product.valueobject.ProductName
import com.mikes.pedido.application.core.domain.product.valueobject.ProductPrice
import com.mikes.pedido.application.port.inbound.order.CreateOrderService
import com.mikes.pedido.application.port.inbound.order.FindOrderService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class OrderControllerTest {
    @MockK
    private lateinit var createOrderService: CreateOrderService

    @MockK
    private lateinit var findOrderService: FindOrderService

    @InjectMockKs
    private lateinit var orderController: OrderController

    @Test
    fun `test find orders with descriptions`() {
        val orderDto =
            OrderDto(
                id = "92ce2145-defc-4bc9-9f77-e45e20b75024",
                number = 1L,
                cpf = "26777903003",
                orderItems =
                    listOf(
                        OrderItemDto(
                            id = "717af226-82bd-48c0-8ed2-b690466c76f8",
                            productName = "produto",
                            productPrice = BigDecimal.valueOf(123),
                            quantity = 1L,
                            price = BigDecimal.valueOf(123),
                        ),
                    ),
                price = BigDecimal.valueOf(123),
                orderStatus = "READY",
                createdAt = date,
                updatedAt = date,
            )

        val order =
            listOf(
                Order.new(
                    id = OrderId.new("92ce2145-defc-4bc9-9f77-e45e20b75024").getOrThrow(),
                    number = OrderNumber.new(1L).getOrThrow(),
                    cpf = Cpf.new("26777903003").getOrThrow(),
                    items =
                        listOf(
                            OrderItem.new(
                                id = OrderItemId.new("717af226-82bd-48c0-8ed2-b690466c76f8").getOrThrow(),
                                productName = ProductName.new("produto").getOrThrow(),
                                productPrice = ProductPrice.new(BigDecimal.valueOf(123)).getOrThrow(),
                                quantity = OrderItemQuantity.new(1L).getOrThrow(),
                            ).getOrThrow(),
                        ),
                    orderStatus = OrderStatus.READY,
                    createdAt = date,
                    updatedAt = date,
                ).getOrThrow(),
            )

        every { findOrderService.findOrdersWithDescriptions() } returns Result.success(order)
        val response = orderController.findOrdersWithDescriptions()
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(listOf(orderDto), response.body)
    }

    @Test
    fun `test create order`() {
        val createOrderRequest =
            CreateOrderRequest(
                cpf = "26777903003",
                listOf(),
            )
        val order =
            Order.new(
                id = OrderId.new("92ce2145-defc-4bc9-9f77-e45e20b75024").getOrThrow(),
                number = OrderNumber.new(1L).getOrThrow(),
                cpf = Cpf.new("26777903003").getOrThrow(),
                items =
                    listOf(
                        OrderItem.new(
                            id = OrderItemId.new("717af226-82bd-48c0-8ed2-b690466c76f8").getOrThrow(),
                            productName = ProductName.new("produto").getOrThrow(),
                            productPrice = ProductPrice.new(BigDecimal.valueOf(123)).getOrThrow(),
                            quantity = OrderItemQuantity.new(1L).getOrThrow(),
                        ).getOrThrow(),
                    ),
                orderStatus = OrderStatus.READY,
                createdAt = date,
                updatedAt = date,
            )
        val orderDto =
            OrderDto(
                id = "92ce2145-defc-4bc9-9f77-e45e20b75024",
                number = 1L,
                cpf = "26777903003",
                orderItems =
                    listOf(
                        OrderItemDto(
                            id = "717af226-82bd-48c0-8ed2-b690466c76f8",
                            productName = "produto",
                            productPrice = BigDecimal.valueOf(123),
                            quantity = 1L,
                            price = BigDecimal.valueOf(123),
                        ),
                    ),
                price = BigDecimal.valueOf(123),
                orderStatus = "READY",
                createdAt = date,
                updatedAt = date,
            )

        every { createOrderService.create(createOrderRequest.toInbound()) } returns order
        val response = orderController.create(createOrderRequest)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(orderDto, response.body)
    }

    @Test
    fun `test find order by id`() {
        val orderId = OrderId.new("92ce2145-defc-4bc9-9f77-e45e20b75024").getOrThrow()
        val order =
            Order.new(
                id = OrderId.new("92ce2145-defc-4bc9-9f77-e45e20b75024").getOrThrow(),
                number = OrderNumber.new(1L).getOrThrow(),
                cpf = Cpf.new("26777903003").getOrThrow(),
                items =
                    listOf(
                        OrderItem.new(
                            id = OrderItemId.new("717af226-82bd-48c0-8ed2-b690466c76f8").getOrThrow(),
                            productName = ProductName.new("produto").getOrThrow(),
                            productPrice = ProductPrice.new(BigDecimal.valueOf(123)).getOrThrow(),
                            quantity = OrderItemQuantity.new(1L).getOrThrow(),
                        ).getOrThrow(),
                    ),
                orderStatus = OrderStatus.READY,
                createdAt = date,
                updatedAt = date,
            ).getOrThrow()

        val orderDto =
            OrderDto(
                id = "92ce2145-defc-4bc9-9f77-e45e20b75024",
                number = 1L,
                cpf = "26777903003",
                orderItems =
                    listOf(
                        OrderItemDto(
                            id = "717af226-82bd-48c0-8ed2-b690466c76f8",
                            productName = "produto",
                            productPrice = BigDecimal.valueOf(123),
                            quantity = 1L,
                            price = BigDecimal.valueOf(123),
                        ),
                    ),
                price = BigDecimal.valueOf(123),
                orderStatus = "READY",
                createdAt = date,
                updatedAt = date,
            )

        every { findOrderService.find(any()) } returns Result.success(order)
        val response = orderController.findOrdersById("92ce2145-defc-4bc9-9f77-e45e20b75024")
        assertEquals(orderDto, response)
    }

    companion object {
        private val date = LocalDateTime.now()
    }
}
