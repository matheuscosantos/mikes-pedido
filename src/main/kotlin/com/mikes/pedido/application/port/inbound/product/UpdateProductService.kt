package com.mikes.pedido.application.port.inbound.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.port.inbound.product.dto.UpdateProductInboundRequest

fun interface UpdateProductService {
    fun update(idValue: String, updateProductInboundRequest: UpdateProductInboundRequest): Result<Product>
}
