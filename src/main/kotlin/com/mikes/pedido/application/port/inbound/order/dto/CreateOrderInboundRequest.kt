package com.mikes.pedido.application.port.inbound.order.dto

data class CreateOrderInboundRequest(
    val customerId: String?,
    val items: List<CreateOrderItemInboundRequest>,
)
