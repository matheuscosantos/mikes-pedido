package com.mikes.pedido.application.port.inbound.order.dto

class OrderProductionInboundRequest(
    val orderId: String,
    val status: String,
)
