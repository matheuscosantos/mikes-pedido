package com.mikes.pedido.application.mapper.order

import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.core.domain.order.OrderItem
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemQuantity
import com.mikes.pedido.application.core.domain.order.valueobject.OrderNumber
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductCategory
import com.mikes.pedido.application.core.domain.product.valueobject.ProductDescription
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.core.domain.product.valueobject.ProductName
import com.mikes.pedido.application.core.domain.product.valueobject.ProductPrice
import com.mikes.pedido.application.port.outbound.order.dto.OrderItemOutboundResponse
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime.now
import java.util.UUID

internal class DefaultOrderDomainMapperTest {
    @Test
    fun `when mapping a order, expect attributes equality`() {
        val id = OrderId.new(UUID.randomUUID().toString()).getOrThrow()
        val orderNumber = OrderNumber.new(1).getOrThrow()
        val customerId = CustomerId.generate()
        val items =
            listOf(
                OrderItem.new(
                    OrderItemId.new(UUID.randomUUID().toString()).getOrThrow(),
                    ProductName.new("name").getOrThrow(),
                    ProductPrice.new(BigDecimal.ONE).getOrThrow(),
                    OrderItemQuantity.new(1).getOrThrow(),
                ).getOrThrow(),
            )
        val orderStatus = OrderStatus.RECEIVED
        val createdAt = now()
        val updatedAt = now()

        val order =
            DefaultOrderDomainMapper().new(
                id,
                orderNumber,
                customerId,
                items,
                orderStatus,
                createdAt,
                updatedAt,
            ).getOrThrow()

        with(order) {
            Assertions.assertEquals(
                id,
                this.id,
            )

            Assertions.assertEquals(
                orderNumber,
                this.number,
            )

            Assertions.assertEquals(
                customerId,
                this.customerId,
            )

            Assertions.assertEquals(
                items,
                this.items,
            )

            Assertions.assertEquals(
                orderStatus,
                this.orderStatus,
            )

            Assertions.assertEquals(
                createdAt,
                this.createdAt,
            )

            Assertions.assertEquals(
                updatedAt,
                this.updatedAt,
            )
        }
    }

    @Test
    fun `when mapping a order with OrderItemOutboundResponse, expect attributes equality`() {
        val id = UUID.randomUUID().toString()
        val number = 1L
        val customerId = CustomerId.generate()
        val items =
            listOf(
                OrderItemOutboundResponse(
                    UUID.randomUUID().toString(),
                    "name",
                    BigDecimal.ONE,
                    1L,
                    BigDecimal.ONE,
                ),
            )
        val price = BigDecimal.ONE
        val orderStatus = OrderStatus.RECEIVED.name
        val createdAt = now()
        val updatedAt = now()

        val order =
            DefaultOrderDomainMapper().new(
                OrderOutboundResponse(
                    id,
                    number,
                    customerId.value,
                    items,
                    price,
                    orderStatus,
                    createdAt,
                    updatedAt,
                ),
            ).getOrThrow()

        with(order) {
            Assertions.assertEquals(
                id,
                this.id.value,
            )

            Assertions.assertEquals(
                number,
                this.number.value,
            )

            Assertions.assertEquals(
                customerId.value,
                this.customerId?.value,
            )

            Assertions.assertEquals(
                orderStatus,
                this.orderStatus.name,
            )

            Assertions.assertEquals(
                createdAt,
                this.createdAt,
            )

            Assertions.assertEquals(
                updatedAt,
                this.updatedAt,
            )
        }
    }

    @Test
    fun `when mapping a order item, expect attributes equality`() {
        val orderItemId = OrderItemId.new(UUID.randomUUID().toString()).getOrThrow()

        val product =
            Product.new(
                ProductId.generate(),
                ProductName.new("product name").getOrThrow(),
                ProductPrice.new(BigDecimal.ONE).getOrThrow(),
                ProductCategory.DRINK,
                ProductDescription.new("product description").getOrThrow(),
                true,
                now(),
                now(),
            ).getOrThrow()

        val orderItemQuantity = OrderItemQuantity.new(1).getOrThrow()

        val orderItem =
            DefaultOrderDomainMapper().new(
                orderItemId,
                product,
                orderItemQuantity,
            ).getOrThrow()

        with(orderItem) {
            Assertions.assertEquals(
                orderItemId,
                this.id,
            )

            Assertions.assertEquals(
                product.name,
                this.productName,
            )

            Assertions.assertEquals(
                product.price,
                this.productPrice,
            )

            Assertions.assertEquals(
                orderItemQuantity,
                this.quantity,
            )
        }
    }

    @Test
    fun `when mapping a order item with OrderItemOutboundResponse, expect attributes equality`() {
        val orderItemId = UUID.randomUUID().toString()
        val productName = "product name"
        val productPrice = BigDecimal.ONE
        val quantity = 1L
        val price = BigDecimal.ONE

        val orderItem =
            DefaultOrderDomainMapper().new(
                OrderItemOutboundResponse(
                    orderItemId,
                    productName,
                    productPrice,
                    quantity,
                    price,
                ),
            ).getOrThrow()

        with(orderItem) {
            Assertions.assertEquals(
                orderItemId,
                this.id.value,
            )

            Assertions.assertEquals(
                productName,
                this.productName.value,
            )

            Assertions.assertEquals(
                productPrice,
                this.productPrice.value,
            )

            Assertions.assertEquals(
                quantity,
                this.quantity.value,
            )

            Assertions.assertEquals(
                price,
                this.price.value,
            )
        }
    }
}
