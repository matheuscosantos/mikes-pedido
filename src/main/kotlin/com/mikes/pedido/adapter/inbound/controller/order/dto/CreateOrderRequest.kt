package com.mikes.pedido.adapter.inbound.controller.order.dto

import com.mikes.pedido.application.port.inbound.order.dto.CreateOrderInboundRequest

data class CreateOrderRequest(
    val cpf: String?,
    val items: List<CreateOrderItemRequest>,
) {
    fun toInbound(): CreateOrderInboundRequest {
        return CreateOrderInboundRequest(
            cpf,
            items.map { it.toInbound() },
        )
    }
}
