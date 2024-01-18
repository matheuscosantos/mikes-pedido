package com.mikes.pedido.application.core.domain.order

import com.fasterxml.jackson.annotation.JsonProperty
import com.mikes.pedido.application.core.domain.customer.valueobject.Cpf
import com.mikes.pedido.application.core.domain.order.valueobject.OrderId
import com.mikes.pedido.application.core.domain.order.valueobject.OrderNumber
import com.mikes.pedido.application.core.domain.order.valueobject.OrderPrice
import com.mikes.pedido.application.core.domain.order.valueobject.OrderStatus
import java.io.Serializable
import java.time.LocalDateTime
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class Order private constructor(
    @JsonProperty("id")
    val id: OrderId,
    @JsonProperty("number")
    val number: OrderNumber,
    @JsonProperty("cpf")
    val cpf: Cpf?,
    @JsonProperty("item")
    val items: List<OrderItem>,
    @JsonProperty("price")
    val price: OrderPrice,
    @JsonProperty("orderStatus")
    val orderStatus: OrderStatus,
    @JsonProperty("createdAt")
    val createdAt: LocalDateTime,
    @JsonProperty("updatedAt")
    val updatedAt: LocalDateTime,
) : Serializable {

    companion object {
        fun new(
            id: OrderId,
            number: OrderNumber,
            cpf: Cpf?,
            items: List<OrderItem>,
            orderStatus: OrderStatus,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
        ): Result<Order> {
            val orderPrice = calculatePrice(items).getOrElse { return failure(it) }

            return success(Order(id, number, cpf, items, orderPrice, orderStatus, createdAt, updatedAt))
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
            cpf,
            items,
            price,
            orderStatus,
            createdAt,
            updatedAt,
        )
    }
}
