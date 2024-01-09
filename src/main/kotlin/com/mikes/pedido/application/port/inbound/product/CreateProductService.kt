package com.mikes.pedido.application.port.inbound.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.port.inbound.product.dto.CreateProductInboundRequest

fun interface CreateProductService {
    fun create(createProductInboundRequest: CreateProductInboundRequest): Result<Product>
}
