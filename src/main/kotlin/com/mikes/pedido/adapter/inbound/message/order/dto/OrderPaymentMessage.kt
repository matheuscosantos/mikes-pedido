package com.mikes.pedido.adapter.inbound.message.order.dto

import com.mikes.pedido.application.port.inbound.order.dto.OrderPaymentInboundRequest

class OrderPaymentMessage(
    val orderId: String,
    val status: String,
) {
    fun toInbound() = OrderPaymentInboundRequest(orderId, status)
}
