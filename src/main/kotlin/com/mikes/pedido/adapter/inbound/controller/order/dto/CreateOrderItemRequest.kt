package com.mikes.pedido.adapter.inbound.controller.order.dto

import com.mikes.pedido.application.port.inbound.order.dto.CreateOrderItemInboundRequest

data class CreateOrderItemRequest(
    val productId: String,
    val quantity: Long,
) {
    fun toInbound(): CreateOrderItemInboundRequest {
        return CreateOrderItemInboundRequest(
            productId,
            quantity,
        )
    }
}
