package com.mikes.pedido.application.port.inbound.order.dto

data class CreateOrderInboundRequest(
    val cpf: String?,
    val items: List<CreateOrderItemInboundRequest>
)
