package com.mikes.pedido.application.core.usecase.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.core.usecase.exception.product.InvalidProductStateException
import com.mikes.pedido.application.core.usecase.exception.product.ProductNotFoundException
import com.mikes.pedido.application.mapper.product.ProductDomainMapper
import com.mikes.pedido.application.port.inbound.product.UpdateProductService
import com.mikes.pedido.application.port.inbound.product.dto.UpdateProductInboundRequest
import com.mikes.pedido.application.port.outbound.product.ProductRepository
import com.mikes.pedido.application.port.outbound.product.dto.ProductOutboundResponse
import com.mikes.pedido.util.mapFailure
import kotlin.Result.Companion.failure

class UpdateProductUseCase(
    private val productRepository: ProductRepository,
    private val productDomainMapper: ProductDomainMapper,
) : UpdateProductService {
    override fun update(
        idValue: String,
        updateProductInboundRequest: UpdateProductInboundRequest,
    ): Result<Product> {
        val toUpdateProduct =
            findActiveProduct(idValue)
                .getOrElse { return failure(it) }

        val updatedProduct =
            productDomainMapper.update(updateProductInboundRequest, toUpdateProduct)
                .getOrElse { return failure(it) }

        return productRepository.save(updatedProduct)
            .toProduct()
            .mapFailure { InvalidProductStateException("Product in invalid state.") }
    }

    private fun findActiveProduct(idValue: String): Result<Product> {
        val productId = ProductId.new(idValue).getOrElse { return failure(it) }

        val productOutboundResponse =
            productRepository.find(productId, active = true)
                ?: return failure(ProductNotFoundException("Product not found."))

        return productOutboundResponse
            .toProduct()
            .mapFailure { InvalidProductStateException("Product in invalid state.") }
    }

    private fun ProductOutboundResponse.toProduct(): Result<Product> {
        return productDomainMapper.new(this)
    }
}
