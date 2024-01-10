package com.mikes.pedido.application.core.usecase.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.core.usecase.exception.product.InvalidProductStateException
import com.mikes.pedido.application.mapper.product.ProductDomainMapper
import com.mikes.pedido.application.port.inbound.product.CreateProductService
import com.mikes.pedido.application.port.inbound.product.dto.CreateProductInboundRequest
import com.mikes.pedido.application.port.outbound.product.ProductRepository
import com.mikes.pedido.application.port.outbound.product.dto.ProductOutboundResponse
import com.mikes.pedido.util.mapFailure
import java.time.LocalDateTime
import kotlin.Result.Companion.failure

class CreateProductUseCase(
    private val productRepository: ProductRepository,
    private val productDomainMapper: ProductDomainMapper,
) : CreateProductService {
    override fun create(createProductInboundRequest: CreateProductInboundRequest): Result<Product> {
        val product =
            createProductInboundRequest
                .newProduct()
                .getOrElse { return failure(it) }

        return productRepository.save(product)
            .toProduct()
            .mapFailure { InvalidProductStateException("Product in invalid state.") }
    }

    private fun CreateProductInboundRequest.newProduct(): Result<Product> {
        val id = ProductId.generate()
        val active = true
        val now = LocalDateTime.now()
        return productDomainMapper.new(this, id, active, now, now)
    }

    private fun ProductOutboundResponse.toProduct(): Result<Product> {
        return productDomainMapper.new(this)
    }
}
