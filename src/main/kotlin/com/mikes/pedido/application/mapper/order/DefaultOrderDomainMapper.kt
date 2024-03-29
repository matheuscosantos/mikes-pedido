package com.mikes.pedido.application.mapper.order

import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.core.domain.order.Order
import com.mikes.pedido.application.core.domain.order.OrderItem
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderItemQuantity
import com.mikes.pedido.application.core.domain.order.valueobject.OrderNumber
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductName
import com.mikes.pedido.application.core.domain.product.valueobject.ProductPrice
import com.mikes.pedido.application.port.outbound.order.dto.OrderItemOutboundResponse
import com.mikes.pedido.application.port.outbound.order.dto.OrderOutboundResponse
import java.time.LocalDateTime
import kotlin.Result.Companion.failure

class DefaultOrderDomainMapper : OrderDomainMapper {
    override fun new(
        id: OrderId,
        orderNumber: OrderNumber,
        nullableCustomerId: CustomerId?,
        items: List<OrderItem>,
        orderStatus: OrderStatus,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    ): Result<Order> {
        return Order.new(
            id,
            orderNumber,
            nullableCustomerId,
            items,
            orderStatus,
            createdAt,
            updatedAt,
        )
    }

    override fun new(
        id: OrderItemId,
        product: Product,
        quantity: OrderItemQuantity,
    ): Result<OrderItem> {
        return OrderItem.new(
            id,
            product.name,
            product.price,
            quantity,
        )
    }

    override fun new(orderOutboundResponse: OrderOutboundResponse): Result<Order> =
        with(orderOutboundResponse) {
            val id = OrderId.new(idValue).getOrElse { return failure(it) }
            val number = OrderNumber.new(numberValue).getOrElse { return failure(it) }
            val customerId = customerIdValue?.let { CustomerId.new(customerIdValue).getOrElse { return failure(it) } }
            val items = items.map { new(it).getOrElse { e -> return failure(e) } }
            val status = OrderStatus.findByValue(orderStatusValue).getOrElse { e -> return failure(e) }

            return Order.new(
                id,
                number,
                customerId,
                items,
                status,
                createdAt,
                updatedAt,
            )
        }

    override fun new(orderItemOutboundResponse: OrderItemOutboundResponse): Result<OrderItem> =
        with(orderItemOutboundResponse) {
            val id = OrderItemId.new(idValue).getOrElse { return failure(it) }
            val productName = ProductName.new(productNameValue).getOrElse { return failure(it) }
            val productPrice = ProductPrice.new(productPriceValue).getOrElse { return failure(it) }
            val quantity = OrderItemQuantity.new(quantityValue).getOrElse { return failure(it) }

            return OrderItem.new(id, productName, productPrice, quantity)
        }
}
