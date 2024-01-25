package com.mikes.pedido.adapter.outbound.database

import com.mikes.pedido.adapter.outbound.database.entity.ProductEntity
import com.mikes.pedido.adapter.outbound.database.jpa.ProductJpaRepository
import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductCategory
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.port.outbound.product.ProductRepository
import com.mikes.pedido.application.port.outbound.product.dto.ProductOutboundResponse
import kotlin.jvm.optionals.getOrNull

class ProductDatabaseRepository(
    private val productJpaRepository: ProductJpaRepository,
) : ProductRepository {
    override fun save(product: Product): ProductOutboundResponse {
        return productJpaRepository.save(ProductEntity.from(product))
            .toOutbound()
    }

    override fun find(
        id: ProductId,
        active: Boolean,
    ): ProductOutboundResponse? {
        return productJpaRepository.findByIdAndActive(id.value, active)
            .map { it.toOutbound() }
            .getOrNull()
    }

    override fun findByCategory(
        productCategory: ProductCategory,
        active: Boolean,
    ): List<ProductOutboundResponse> {
        return productJpaRepository.findAllByCategoryAndActive(productCategory.name, active)
            .map { it.toOutbound() }
    }
}
