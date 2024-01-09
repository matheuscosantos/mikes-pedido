package com.mikes.pedido.application.core.usecase.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.core.usecase.exception.product.InvalidProductStateException
import com.mikes.pedido.application.core.usecase.exception.product.ProductNotFoundException
import com.mikes.pedido.application.mapper.product.ProductDomainMapper
import com.mikes.pedido.application.port.inbound.product.DeleteProductService
import com.mikes.pedido.application.port.outbound.product.ProductRepository
import com.mikes.pedido.application.port.outbound.product.dto.ProductOutboundResponse
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class DeleteProductUseCase(
    private val productRepository: ProductRepository,
    private val productDomainMapper: ProductDomainMapper,
) : DeleteProductService {
    override fun delete(idValue: String): Result<Unit> {
        val productId =
            ProductId.new(idValue)
                .getOrElse { return failure(it) }

        val productOutboundResponse =
            productRepository.find(productId, active = true)
                ?: return failure(ProductNotFoundException("Product not found."))

        val product =
            productOutboundResponse
                .toProduct()
                .map { it.toInactive() }
                .getOrElse { return failure(InvalidProductStateException("Product in invalid state.")) }

        productRepository.save(product)

        return success(Unit)
    }

    private fun ProductOutboundResponse.toProduct(): Result<Product> {
        return productDomainMapper.new(this)
    }
}
