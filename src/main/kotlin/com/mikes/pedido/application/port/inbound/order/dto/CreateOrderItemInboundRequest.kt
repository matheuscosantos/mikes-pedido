package com.mikes.pedido.application.port.inbound.order.dto

data class CreateOrderItemInboundRequest(
    val productId: String,
    val quantity: Long,
)
