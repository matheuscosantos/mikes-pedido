package com.mikes.pedido.application.port.outbound.order.dto

import java.math.BigDecimal

class OrderReceivedMessage(
    val orderId: String,
    val value: BigDecimal,
)
