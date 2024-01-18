package com.mikes.pedido.adapter.inbound.controller.order.dto

import com.mikes.pedido.application.core.domain.order.OrderItem
import java.io.Serializable
import java.math.BigDecimal

data class OrderItemDto(
    val id: String,
    val productName: String,
    val productPrice: BigDecimal,
    val quantity: Long,
    val price: BigDecimal,
) : Serializable {
    companion object {
        fun from(orderItem: OrderItem): OrderItemDto =
            with(orderItem) {
                OrderItemDto(
                    id.value,
                    productName.value,
                    productPrice.value,
                    quantity.value,
                    price.value,
                )
            }
    }
}
