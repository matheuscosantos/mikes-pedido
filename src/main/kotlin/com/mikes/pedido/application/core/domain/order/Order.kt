package com.mikes.pedido.application.core.domain.order

import com.mikes.pedido.application.core.domain.customer.valueobject.CustomerId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderNumber
import com.mikes.pedido.application.core.domain.order.valueobject.OrderPrice
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import java.io.Serializable
import java.time.LocalDateTime
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class Order private constructor(
    val id: OrderId,
    val number: OrderNumber,
    val customerId: CustomerId?,
    val items: List<OrderItem>,
    val price: OrderPrice,
    val orderStatus: OrderStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) : Serializable {
    companion object {
        fun new(
            id: OrderId,
            number: OrderNumber,
            customerId: CustomerId?,
            items: List<OrderItem>,
            orderStatus: OrderStatus,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
        ): Result<Order> {
            val orderPrice = calculatePrice(items).getOrElse { return failure(it) }

            return success(Order(id, number, customerId, items, orderPrice, orderStatus, createdAt, updatedAt))
        }

        private fun calculatePrice(orderItems: List<OrderItem>): Result<OrderPrice> {
            val orderPriceValue = orderItems.sumOf { it.price.value }
            return OrderPrice.new(orderPriceValue)
        }
    }

    fun updateStatus(orderStatus: OrderStatus): Order {
        val updatedAt = LocalDateTime.now()

        return Order(
            id,
            number,
            customerId,
            items,
            price,
            orderStatus,
            createdAt,
            updatedAt,
        )
    }
}
