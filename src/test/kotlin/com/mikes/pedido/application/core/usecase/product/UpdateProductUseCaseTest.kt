package com.mikes.pedido.application.core.usecase.product

import com.mikes.pedido.application.core.domain.product.Product
import com.mikes.pedido.application.core.domain.product.valueobject.ProductId
import com.mikes.pedido.application.mapper.product.ProductDomainMapper
import com.mikes.pedido.application.port.outbound.product.ProductRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.Result.Companion.success

internal class UpdateProductUseCaseTest {
    @Test
    fun a() {
        val expectedProduct = mockk<Product>()
        val productId = ProductId.generate().value

        val productRepository = mockkProductRepository()
        val productDomainMapper = mockkProductDomainMapper(expectedProduct)

        val updateProductUseCase = UpdateProductUseCase(productRepository, productDomainMapper)

        val product =
            updateProductUseCase.update(productId, mockk())
                .getOrThrow()

        Assertions.assertEquals(expectedProduct, product)
    }

    private fun mockkProductRepository() =
        mockk<ProductRepository>().also {
            every { it.save(any()) } returns mockk()
            every { it.find(any(), any()) } returns mockk()
        }

    private fun mockkProductDomainMapper(product: Product) =
        mockk<ProductDomainMapper>().also {
            every { it.new(any()) } returns success(product)
            every { it.update(any(), any()) } returns success(mockk())
        }
}
