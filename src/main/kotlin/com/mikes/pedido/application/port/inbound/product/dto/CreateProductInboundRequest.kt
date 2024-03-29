package com.mikes.pedido.application.port.inbound.product.dto

import java.math.BigDecimal

data class CreateProductInboundRequest(
    val name: String,
    val price: BigDecimal,
    val category: String,
    val description: String,
)
