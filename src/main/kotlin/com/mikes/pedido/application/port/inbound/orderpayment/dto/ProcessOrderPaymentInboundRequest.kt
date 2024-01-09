package com.mikes.pedido.application.port.inbound.orderpayment.dto

data class ProcessOrderPaymentInboundRequest(
    val orderId: String,
    val paid: Boolean
)
