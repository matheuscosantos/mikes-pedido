package com.mikes.pedido.application.port.outbound.order.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderOutboundResponse(
    val idValue: String,
    val numberValue: Long,
    val customerIdValue: String?,
    val items: List<OrderItemOutboundResponse>,
    val priceValue: BigDecimal,
    val orderStatusValue: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
