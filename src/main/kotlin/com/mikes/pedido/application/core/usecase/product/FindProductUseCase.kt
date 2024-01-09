package com.mikes.pedido.application.core.usecase.product

import br.com.fiap.mikes.util.mapFailure
import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductCategory
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.core.usecase.exception.product.InvalidProductStateException
import com.mikes.pedido.application.core.usecase.exception.product.ProductNotFoundException
import com.mikes.pedido.application.mapper.product.ProductDomainMapper
import com.mikes.pedido.application.port.inbound.product.FindProductService
import com.mikes.pedido.application.port.outbound.product.ProductRepository
import com.mikes.pedido.application.port.outbound.product.dto.ProductOutboundResponse
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class FindProductUseCase(
    private val productRepository: ProductRepository,
    private val productDomainMapper: ProductDomainMapper,
) : FindProductService {
    override fun findAll(
        productCategoryValue: String,
        active: Boolean,
    ): Result<List<Product>> {
        val productCategory =
            ProductCategory.new(productCategoryValue)
                .getOrElse { return failure(it) }

        val products =
            productRepository.findByCategory(productCategory, active)
                .map { it.toProduct().getOrElse { return failure(InvalidProductStateException("Product in invalid state.")) } }

        return success(products)
    }

    override fun find(
        idValue: String,
        active: Boolean,
    ): Result<Product> {
        val productId =
            ProductId.new(idValue)
                .getOrElse { return failure(it) }

        val productOutboundResponse =
            productRepository.find(productId, active)
                ?: return failure(ProductNotFoundException("Product not found."))

        return productOutboundResponse
            .toProduct()
            .mapFailure { InvalidProductStateException("Product in invalid state.") }
    }

    private fun ProductOutboundResponse.toProduct(): Result<Product> {
        return productDomainMapper.new(this)
    }
}
