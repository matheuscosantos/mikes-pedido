package com.mikes.pedido.application.port.inbound.order.dto

data class OrderPaymentInboundRequest(
    val orderId: String,
    val status: String,
)
