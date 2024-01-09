package com.mikes.pedido.application.port.inbound.orderpayment.dto

data class ConsultOrderPaymentStatusRequest(
    val orderId: String,
)
