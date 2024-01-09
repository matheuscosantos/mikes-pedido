package com.mikes.pedido.application.mapper.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.port.inbound.product.dto.CreateProductInboundRequest
import com.mikes.pedido.application.port.inbound.product.dto.UpdateProductInboundRequest
import com.mikes.pedido.application.port.outbound.product.dto.ProductOutboundResponse
import java.time.LocalDateTime

interface ProductDomainMapper {
    fun new(
        createProductInboundRequest: CreateProductInboundRequest,
        id: ProductId,
        active: Boolean,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    ): Result<Product>

    fun new(productOutboundResponse: ProductOutboundResponse): Result<Product>

    fun update(
        updateProductInboundRequest: UpdateProductInboundRequest,
        product: Product,
    ): Result<Product>
}
