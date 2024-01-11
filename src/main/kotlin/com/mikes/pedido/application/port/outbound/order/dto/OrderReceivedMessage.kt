package com.mikes.pedido.application.port.outbound.order.dto

import java.math.BigDecimal

data class OrderReceivedMessage(
    val orderId: String,
    val value: BigDecimal,
)
