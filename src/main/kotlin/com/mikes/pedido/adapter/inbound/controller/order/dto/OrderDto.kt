package com.mikes.pedido.adapter.inbound.controller.order.dto

import com.mikes.pedido.application.core.domain.order.Order
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderDto(
    val id: String,
    val number: Long,
    val customerId: String?,
    val orderItems: List<OrderItemDto>,
    val price: BigDecimal,
    val orderStatus: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) : Serializable {
    companion object {
        fun from(order: Order): OrderDto =
            with(order) {
                OrderDto(
                    id.value,
                    number.value,
                    customerId?.value,
                    items.map { OrderItemDto.from(it) },
                    price.value,
                    orderStatus.name,
                    createdAt,
                    updatedAt,
                )
            }
    }
}
