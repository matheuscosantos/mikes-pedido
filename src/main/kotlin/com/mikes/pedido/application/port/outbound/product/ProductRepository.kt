package com.mikes.pedido.application.port.outbound.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductCategory
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.port.outbound.product.dto.ProductOutboundResponse

interface ProductRepository {
    fun save(product: Product): ProductOutboundResponse
    fun find(id: ProductId, active: Boolean): ProductOutboundResponse?
    fun findByCategory(productCategory: ProductCategory, active: Boolean): List<ProductOutboundResponse>
}
