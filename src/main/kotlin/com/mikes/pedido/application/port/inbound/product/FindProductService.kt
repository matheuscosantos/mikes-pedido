package com.mikes.pedido.application.port.inbound.product

import com.mikes.pedido.application.core.domain.product.Product

interface FindProductService {
    fun findAll(productCategoryValue: String, active: Boolean): Result<List<Product>>
    fun find(idValue: String, active: Boolean): Result<Product>
}
