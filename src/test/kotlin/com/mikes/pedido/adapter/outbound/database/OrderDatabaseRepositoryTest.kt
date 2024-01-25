package com.mikes.pedido.adapter.outbound.database

import com.mikes.pedido.adapter.outbound.database.entity.OrderEntity
import com.mikes.pedido.adapter.outbound.database.entity.OrderItemEntity
import com.mikes.pedido.adapter.outbound.database.jpa.OrderItemJpaRepository
import com.mikes.pedido.adapter.outbound.database.jpa.OrderJpaRepository
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
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class OrderDatabaseRepositoryTest {
    @Test
    fun testFindNumber() {
        val orderJpaRepository = mockk<OrderJpaRepository>()
        val orderItemJpaRepository = mockk<OrderItemJpaRepository>()

        val orderDatabaseRepository = OrderDatabaseRepository(orderJpaRepository, orderItemJpaRepository)

        every { orderJpaRepository.findNumber() } returns 42

        val result = orderDatabaseRepository.findNumber()

        assertEquals(42, result)
    }

    @Test
    fun testSave() {
        val orderJpaRepository = mockk<OrderJpaRepository>()
        val orderItemJpaRepository = mockk<OrderItemJpaRepository>()

        val orderDatabaseRepository = OrderDatabaseRepository(orderJpaRepository, orderItemJpaRepository)

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

        every { orderJpaRepository.save(any()) } answers { firstArg() }
        every { orderItemJpaRepository.save(any()) } answers { firstArg() }

        val result = orderDatabaseRepository.save(order)

        assertNotNull(result)
    }

    @Test
    fun testFindOrdersWithDescriptions() {
        val orderJpaRepository = mockk<OrderJpaRepository>()
        val orderItemJpaRepository = mockk<OrderItemJpaRepository>()

        val orderDatabaseRepository = OrderDatabaseRepository(orderJpaRepository, orderItemJpaRepository)

        val orderEntity =
            OrderEntity(
                id = "c3dc25a4-bdc6-49b5-9dd5-10d7418062b5",
                number = 1L,
                cpf = "26777903003",
                price = BigDecimal.valueOf(1),
                status = "READY",
                createdAt = date,
                updatedAt = date,
            )

        every { orderJpaRepository.findOrdersWithDescriptions() } returns listOf(orderEntity)
        every { orderItemJpaRepository.findByOrderId(any()) } returns
            listOf(
                OrderItemEntity(
                    id = "e21b75ef-d513-4568-bc87-868f41072e6e",
                    orderId = "c3dc25a4-bdc6-49b5-9dd5-10d7418062b5",
                    price = BigDecimal.valueOf(1),
                    productName = "produto",
                    productPrice = BigDecimal.valueOf(1),
                    quantity = 1L,
                ),
            )

        val result = orderDatabaseRepository.findOrdersWithDescriptions()

        assertNotNull(result)
    }

    @Test
    fun testFindById() {
        val orderJpaRepository = mockk<OrderJpaRepository>()
        val orderItemJpaRepository = mockk<OrderItemJpaRepository>()

        val orderDatabaseRepository = OrderDatabaseRepository(orderJpaRepository, orderItemJpaRepository)

        val orderId = OrderId.new("c3dc25a4-bdc6-49b5-9dd5-10d7418062b5")
        val orderEntity =
            OrderEntity(
                id = "c3dc25a4-bdc6-49b5-9dd5-10d7418062b5",
                number = 1L,
                cpf = "26777903003",
                price = BigDecimal.valueOf(1),
                status = "READY",
                createdAt = date,
                updatedAt = date,
            )

        every { orderJpaRepository.findById(any()) } returns Optional.of(orderEntity)
        every { orderItemJpaRepository.findByOrderId(any()) } returns
            listOf(
                OrderItemEntity(
                    id = "e21b75ef-d513-4568-bc87-868f41072e6e",
                    orderId = "c3dc25a4-bdc6-49b5-9dd5-10d7418062b5",
                    price = BigDecimal.valueOf(1),
                    productName = "produto",
                    productPrice = BigDecimal.valueOf(1),
                    quantity = 1L,
                ),
            )

        val result = orderDatabaseRepository.findById(orderId.getOrThrow())

        assertNotNull(result)
    }

    companion object {
        private val date = LocalDateTime.now()
    }
}
