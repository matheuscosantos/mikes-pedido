package com.mikes.pedido.adapter.inbound.controller.product.dto

import com.mikes.pedido.application.port.inbound.product.dto.CreateProductInboundRequest
import java.math.BigDecimal

data class CreateProductRequest(
    val name: String,
    val price: BigDecimal,
    val category: String,
    val description: String,
) {
    fun toInbound() = CreateProductInboundRequest(name, price, category, description)
}
