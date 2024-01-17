package com.mikes.pedido.adapter.inbound.message.order.dto

import com.mikes.pedido.application.port.inbound.order.dto.OrderProductionInboundRequest

class OrderProductionMessage(
    val orderId: String,
    val status: String,
) {
    fun toInbound() = OrderProductionInboundRequest(orderId, status)
}
