package com.mikes.pedido.application.port.outbound.order.dto

import java.math.BigDecimal

data class OrderItemOutboundResponse(
    val idValue: String,
    val productNameValue: String,
    val productPriceValue: BigDecimal,
    val quantityValue: Long,
    val priceValue: BigDecimal,
)
